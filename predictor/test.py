from PIL import Image
import os
import random
import xml.etree.ElementTree as ET

from Main import model

dataset_dir = "E:\\读书\\2022-2023-1\\毕业设计\\a6\\a\\JPEGImages"
label_dir = "E:\\读书\\2022-2023-1\\毕业设计\\a6\\a\\Annotations"
save_dir = "F:\\java_IDEA\\mol\\predictor\\result\\test"
my_model = model.Model()
images = []
labels = []


# 降低图片分辨率
def ResizeImage(filein, fileout, width, height):
    """
    改变图片大小
    :param filein: 输入图片的文件夹路径
    :param fileout: 输出图片的文件夹路径
    :param width: 输出图片宽度
    :param height: 输出图片宽度
    :return:
    """
    allImage = os.listdir(filein)
    allImage = random.sample(allImage, k=100)  # 随机取100个值
    for image in allImage:
        img = Image.open(filein + "/" + image)
        images.append(save_dir + "/" + image)
        labels.append(label_dir + "/" + image.replace('jpg', 'xml'))
        type = img.format
        out = img.resize((int(width), int(height)), Image.ANTIALIAS)
        # 第二个参数：
        # Image.NEAREST ：低质量
        # Image.BILINEAR：双线性
        # Image.BICUBIC ：三次样条插值
        # Image.ANTIALIAS：高质量
        out.save(fileout + "/" + image, type)
        print("Resize: {}".format(image))


# python删除文件的方法 os.remove(path)path指的是文件的绝对路径,如：
def del_file(path_data):
    """
    删除文件夹下所有文件
    :param path_data: 输入图片的文件夹路径
    :return:
    """
    for i in os.listdir(path_data):  # os.listdir(path_data)#返回一个列表，里面是当前目录下面的所有东西的相对路径
        file_data = path_data + "\\" + i  # 当前文件夹的下面的所有东西的绝对路径
        if os.path.isfile(file_data):  # os.path.isfile判断是否为文件,如果是文件,就删除.如果是文件夹.递归给del_file.
            os.remove(file_data)
        else:
            del_file(file_data)


if __name__ == "__main__":
    del_file(save_dir)  # 删除原来的文件
    ResizeImage(dataset_dir, save_dir, 300, 300)  # 生成低分辨率图片
    t = 0
    no_sr_correct = 0
    sr_correct = 0
    # 对每张图片进行测试
    for i in range(len(images)):
        img = images[i]
        label = labels[i]
        result = my_model.test_predict(img, sr=False)
        result = sorted(result, key=lambda x: -x['score'])
        if len(result) == 0:
            break
        print('{} {}'.format(result[0]['category'], result[0]['score']))
        sr_result = my_model.test_predict(img, sr=True)
        sr_result = sorted(sr_result, key=lambda x: -x['score'])
        if len(result) == 0:
            break
        print('{} {}'.format(sr_result[0]['category'], sr_result[0]['score']))
        label_name = ET.parse(label).getroot().iter('name')
        label = ""
        for res in label_name:
            label = res.text
        if sr_result[0]['category'] == label:
            sr_correct = sr_correct + 1
        if result[0]['category'] == label:
            no_sr_correct = no_sr_correct + 1
        if sr_result[0]['category'] == label and sr_result[0]['score'] > result[0]['score']:
            t = t + 1
            print('SR Win {}!'.format(t))
        print("---------test: {}-------------".format(img))
    print('total: SR Win {}!'.format(t))
    print("sr_correct: {}".format(sr_correct))
    print("no_sr_correct: {}".format(no_sr_correct))
