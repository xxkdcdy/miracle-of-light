package com.cdy.mol.backend.controller.user.account;

import com.cdy.mol.backend.service.user.account.UploadPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UploadPhotoController {
    @Autowired
    private UploadPhotoService uploadPhotoService;

    @PostMapping("/api/user/account/photo/")
    public Map<String, String> upload(@RequestParam Map<String, String> data) {
        String photo = data.get("photo");
        return uploadPhotoService.upload(photo);
    }
}
