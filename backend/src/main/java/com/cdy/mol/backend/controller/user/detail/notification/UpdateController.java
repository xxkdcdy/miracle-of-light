package com.cdy.mol.backend.controller.user.detail.notification;

import com.cdy.mol.backend.service.user.detail.notification.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateController {
    @Autowired
    private UpdateService updateService;

    @PostMapping("/api/user/detail/notification/update/")
    public Map<String, String> updateNotification(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        return updateService.update(id);
    }
}
