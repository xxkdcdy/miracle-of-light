package com.cdy.mol.backend.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class OSSUtil {
    // 将base64图片文件存到OSS对应目录中
    public static String uploadImage(String objectName, String base64) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5t8jz3fZ3sB9JZUEi7U3";
        String accessKeySecret = "Swskt0DY1HWv0tTseviH7OOhtyEes6";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "miracle-of-light";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        // String objectName = "user/account/photo/exampleobject.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String resultMessage = "unknown error!";

        try {
            // 填写字符串，content为存储内容。
            // 创建PutObjectRequest对象。
            // 示例代码 PutObjectRequest putObjectRequest = new PutObjectRequest(
            // bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, base64ConvertPNG(base64));

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
             ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
             metadata.setObjectAcl(CannedAccessControlList.PublicRead);   // 公共读
             putObjectRequest.setMetadata(metadata);

            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            System.out.println(result.getResponse().getStatusCode());
            if (200 == result.getResponse().getStatusCode()) resultMessage = "success";
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            resultMessage = ce.getMessage();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            return resultMessage;
        }
    }

    public static void deleteImage(String objectName) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "";
        String accessKeySecret = "";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "miracle-of-light";
        // 填写文件完整路径objectName。文件完整路径中不能包含Bucket名称。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucketName, objectName);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public static InputStream base64ConvertPNG(String base64){
        //处理base64字符串
        base64 = base64.replaceAll(" ", "+").replace("data:image/png;base64,", "")
                .replace("data:image/jpeg;base64,", "");
        try {
            byte[] buffer = Base64.decodeBase64(base64);
            for(int i = 0;i<buffer.length;i++){
                if(buffer[i] < 0){
                    buffer[i] += 256;
                }
            }
            // 生成流
            ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
            return stream;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
