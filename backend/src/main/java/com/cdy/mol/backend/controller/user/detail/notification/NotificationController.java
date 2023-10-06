package com.cdy.mol.backend.controller.user.detail.notification;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.user.detail.notification.GetNotificationService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NotificationController {
    @Autowired
    private GetNotificationService getNotificationService;
    @GetMapping("/api/user/detail/notification/getlist/")
    public JSONObject getNotification(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getNotificationService.getList(page);
    }
}
