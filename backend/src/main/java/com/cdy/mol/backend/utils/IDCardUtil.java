package com.cdy.mol.backend.utils;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class IDCardUtil {
    public static boolean checkIDCard(String idCard, String name) {
        String host = "https://slysmrzgzl.market.alicloudapi.com";
        String path = "/get/idcard/checkV2";
        String method = "GET";
        String appcode = "814ee0e1c54847ec898b5a96acd49562";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("idcard", idCard);
        querys.put("name", name);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            String result = EntityUtils.toString(response.getEntity());
            if (result == null || !result.contains("desc\":\"一致")) return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
