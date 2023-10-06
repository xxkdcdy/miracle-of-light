package com.cdy.mol.backend.controller.user.detail.notification;

import com.cdy.mol.backend.service.user.detail.notification.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveController {
    @Autowired
    private RemoveService removeService;
    @PostMapping("/api/user/detail/notification/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        return removeService.remove(id);
    }
}
