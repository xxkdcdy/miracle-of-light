## 一个伟大的林业昆虫智能识别管理系统     
（毕业设计项目）    

***
### 前端依赖项      
@vue/cli@4.5.11      
Port: 8080      

| 名称 | 版本 | 描述 |      
| ------ | ------ | ------ |     
| vue-router | 4.5.19 | vue3路由库 |      
| vue-vuex | 4.5.19 | vue3缓存库 |      
| bootstrap | 5.2.3 | 样式库 |      
| jquery | 3.6.3 | http请求库 |      
| core-js | 3.27.1 | bootstrap依赖样式库 |      
| @popperjs/core | 2.11.6 | bootstrap依赖样式库 |      
| ailabel | 5.1.15 | 标注库 |      
| xlsx | 0.18.5 | Excel库 |       
| x2js | 3.4.4 | XML库 |      
| vkbeautify | 0.99.3 | XML美化库 |      

***
### 后端依赖项     
spring-boot-starter-parent@2.7.1       
Port: 3000        
Java17

| 名称 | 版本 | 描述 |      
| ------ | ------ | ------ |      
| spring-boot-starter-jdbc | 3.0.0 | Springboot数据库连接库 |      
| lombok | 1.18.24 | Java注解库 |      
| mysql-connector-java | 8.0.29 | MySQL连接库 |      
| mybatis-plus-boot-starter | 3.5.2 | MybatisPlus数据库操作库 |      
| mybatis-plus-generator | 3.5.3 | MybatisPlus导入库 |      
| spring-boot-starter-security | 2.7.1 | Springboot安全组件 |      
| jjwt-api | 0.11.5 | jwt导入库 |      
| jjwt-impl | 0.11.5 | jwt依赖库 |      
| jjwt-jackson | 0.11.5 | jwt生成token组件 |      
| aliyun-sdk-oss | 3.15.1 | 阿里云OSS依赖 |      
| jaxb-api | 2.3.1 | 阿里云OSS依赖 |      
| activation | 1.1.1 | 阿里云OSS依赖 |      
| jaxb-runtime | 2.3.3 | 阿里云OSS依赖 |      
| spring-boot-starter-mail | 2.7.3 | 邮箱依赖 |      
| fastjson | 2.0.11 | json依赖 |      

***     
Flask@2.2.3      
Port：3001       
Python3.7

| 名称 | 版本 | 描述 |      
| ------ | ------ | ------ |      
| python-dev | 2.7.17 | python环境依赖 |      
| python3-dev | 3.8.2-0ubuntu2 | python环境依赖 |      
| libpython3.7-dev | 3.7.16 | python环境依赖 |      
| chardet | 5.1.0 | paddlepaddle依赖 |      
| pycocotools | 2.0.6 | paddlepaddle依赖 |      
| paddlepaddle | 1.8.5 | Paddle平台 |      
| scikit-build | 0.16.7 | paddlex依赖 |      
| cmake | 3.25.2 | paddlex依赖 |      
| paddlex | 1.3.0 | Paddle平台预测框架 |      
| flask | 2.2.3 | 网络连接库 |      
| flask_cors | 3.0.10 | flask跨域请求库 |        
| torch | 1.12.1 | 超分辨率模型框架 |      
| h5py | 3.7.0 | h5文件数据读取 |      

***
### 开发日志     

#### 2023/5/13          
main:bug修复        
修复了一个bug，该bug曾导致对输入图像尺寸过大的图像进行超分辨率时，Flask崩溃的问题。         
现在系统只会对 600 * 600 像素以上的图片进行超分辨率。        

#### 2023/4/30      
main:三阶段开发完成        
引入了SRCNN作为超分辨率，完善了YoloV3-MobileNetV3识别模型。         

#### 2023/3/18       
main:二阶段开发完成         
增加了Web端积分排行榜，无权限页面，所有用户可以查看积分排名。       
增加了Web端主页。       
增加了Web端管理员界面，根据用户flag显示页面入口，在后端通过token用户的flag判断权限，防止越权。      

#### 2023/3/12      
main:二阶段第一部分       
移动端数据库接入，统一关键字。     
移动端支持在线识别，Web端完成数据标注上传。      
移动端支持检查更新。         

#### 2023/3/9     
main:一阶段开发完成，项目测试上线完成      
增加了Flask后端，用于部署识别模型。     
前端核心识别模块，数据标注功能，个人信息模块开发完成。     
将昆虫数据整合进一个数据库。      
下一阶段进行移动端数据适配，完成版本更新逻辑，统一数据标注方式。      
主要将前端管理员模块，积分平台和主页完成，完成前端开发。      
（PS：因为上传大小限制，之前的提交上传了模型，导致无法维护了，重新开了一个仓库，以后一定认真ignore T T）     

#### 2023/1/30     
main:个人信息模块开发     
个人信息模块开发完成，WEB端支持用户管理功能，用户信息接入腾讯兔小巢。      
租用阿里云OSS服务，将用户头像保存在云端，方便访问和读取。      

#### 2023/1/15     
main:项目立项，环境搭建      
前后端项目创建完成，使用前后端分离的开发模式。    