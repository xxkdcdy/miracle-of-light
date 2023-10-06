import argparse
from numpy.lib.function_base import average
import torch
import torch.backends.cudnn as cudnn
import numpy as np
import PIL.Image as pil_image
from torch import nn
import math
from Main.utils import convert_rgb_to_ycbcr, convert_ycbcr_to_rgb, calc_psnr
import os


class GELU(nn.Module):
    """
    Paper Section 3.4, last paragraph notice that BERT used the GELU instead of RELU
    """

    def forward(self, x):
        return 0.5 * x * (1 + torch.tanh(math.sqrt(2 / math.pi) * (x + 0.044715 * torch.pow(x, 3))))


class SRCNN(nn.Module):
    def __init__(self, num_channels=1):
        super(SRCNN, self).__init__()
        self.conv1 = nn.Conv2d(num_channels, 128, kernel_size=9, padding=9 // 2)
        self.conv2 = nn.Conv2d(128, 64, kernel_size=5, padding=5 // 2)
        self.conv3 = nn.Conv2d(64, num_channels, kernel_size=5, padding=5 // 2)
        self.relu = nn.ReLU(inplace=True)

    def forward(self, x):
        x = self.relu(self.conv1(x))
        x = self.relu(self.conv2(x))
        x = self.conv3(x)
        return x


def work(sr_model, sr_image, args, device):
    # 读取图片
    image = pil_image.open(sr_image).convert('RGB')

    if image.width >= 600 or image.height >= 600:
        return

    # image = cv2.imread(args.image).convert('RGB')
    # print(image.shape)
    # 将图片执行双三次插值
    image_width = (image.width // args.scale) * args.scale * args.scale
    image_height = (image.height // args.scale) * args.scale * args.scale
    image = image.resize((image_width, image_height), resample=pil_image.BICUBIC)
    image = image.resize((image.width // args.scale, image.height // args.scale), resample=pil_image.BICUBIC)
    image = image.resize((image.width * args.scale, image.height * args.scale), resample=pil_image.BICUBIC)
    # image.save(args.image.replace('.', '_bicubic_x{}.'.format(args.scale)))
    # print('finish BICUBIC')

    # 图片转换为矩阵
    image = np.array(image).astype(np.float32)
    ycbcr = convert_rgb_to_ycbcr(image)

    new_images = []
    psnr = []
    for i in range(3):
        y = ycbcr[..., i]
        y /= 255.
        y = torch.from_numpy(y).to(device)
        y = y.unsqueeze(0).unsqueeze(0)

        with torch.no_grad():
            preds = sr_model(y).clamp(0.0, 1.0)

        psnr.append(calc_psnr(y, preds).cpu())

        preds = preds.mul(255.0).cpu().numpy().squeeze(0).squeeze(0)
        new_images.append(preds)

    print('PSNR: {:.2f}'.format(average(psnr)))

    # output = np.array([preds, ycbcr[..., 1], ycbcr[..., 2]]).transpose([1, 2, 0])
    output = np.array([new_images[0], new_images[1], new_images[2]]).transpose([1, 2, 0])
    output = np.clip(convert_ycbcr_to_rgb(output), 0.0, 255.0).astype(np.uint8)
    output = pil_image.fromarray(output)
    # output.save(args.image.replace('.', '_srcnn_x{}.'.format(args.scale)))
    output.save(sr_image.replace('pic', '_srcnn_x2'))
    # print('successfully save the image')
