# -*- coding : utf-8 -*-
# coding: utf-8

import paddlex as pdx
import json
import Constraint
import base64
import paddle
from Main import use
import argparse
import torch
import torch.backends.cudnn as cudnn
from Main.use import work
import subprocess


class Model:
    model = pdx.load_model(Constraint.model_path)

    # 加载参数
    parser = argparse.ArgumentParser()
    parser.add_argument('--weights-file', default='./Main/inference_model/best.pth', type=str)
    parser.add_argument('--dir', type=str, default='./pic/')
    parser.add_argument('--scale', type=int, default=2)
    args = parser.parse_args()

    # 设备设置
    cudnn.benchmark = True
    device = torch.device('cuda:0' if torch.cuda.is_available() else 'cpu')

    sr_model = use.SRCNN().to(device)

    state_dict = sr_model.state_dict()
    for n, p in torch.load(args.weights_file, map_location=lambda storage, loc: storage).items():
        if n in state_dict.keys():
            state_dict[n].copy_(p)
        else:
            raise KeyError(n)
    sr_model.eval()

    """主要处理类"""

    def __init__(self):
        print(paddle.__version__)

    def predict(self, img, appKey, use_sr="0"):
        if appKey != "iuaena0516":
            data = {
                'error_message': 'appKey错误'
            }
            response = json.dumps(data)
            return response, 200, {"Content-Type": "application/json"}
        p = img.find('base64,')
        if p == -1:
            data = {
                'error_message': '参数错误'
            }
            response = json.dumps(data)
            return response, 200, {"Content-Type": "application/json"}
        else:
            test_img = Constraint.save_dir + 'input.png'
            head, context = img.split(",")  # 将base64_str以“,”分割为两部分
            img_data = base64.b64decode(context)  # 解码时只要内容部分
            # 将图片保存为文件
            with open(test_img, 'wb') as f:
                f.write(img_data)
                # 超分辨率
                if use_sr == "1":
                    work(self.sr_model, test_img, self.args, self.device)
                    # p = subprocess.Popen(
                    #     ["D:/BaiduNetdiskDownload/ESRGAN/realesrgan-ncnn-vulkan.exe", "-i", test_img, "-o", test_img])
                    # p.wait()

            result = self.model.predict(test_img)
            pdx.det.visualize(test_img, result, threshold=0.1, save_dir=Constraint.save_dir)
            # 第一种
            data = {
                'error_message': 'success',
                'result': result,
                'img': "data:image/jpeg;base64," + return_img_stream(Constraint.save_dir + "visualize_input.png"),
            }
            response = json.dumps(data)  # 将python的字典转换为json字符串
            return response, 200, {"Content-Type": "application/json"}

    def test_predict(self, input_img, sr=True):
        if sr:
            work(self.sr_model, input_img, self.args, self.device)
            # p = subprocess.Popen(
            #     ["D:/BaiduNetdiskDownload/ESRGAN/realesrgan-ncnn-vulkan.exe", "-i", input_img, "-o", input_img])
            # p.wait()
        return self.model.predict(input_img)


def return_img_stream(img_local_path):
    """
    工具函数:
    获取本地图片流
    :param img_local_path:文件单张图片的本地绝对路径
    :return: 图片流
    """
    with open(img_local_path, 'rb') as img_f:
        img_stream = img_f.read()
        img_stream = base64.b64encode(img_stream)
        # with open('001.jpg', 'wb') as f:
        #     f.write(base64.b64decode(img_stream))
        # 返回时去掉二进制标记
        return str(img_stream)[2:-1]
    return ''
