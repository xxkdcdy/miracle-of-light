package com.cdy.mol.backend.controller.manage.update;

import com.cdy.mol.backend.service.manage.update.VersionUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VersionUpdateController {
    @Autowired
    private VersionUpdateService versionUpdateService;
    @PostMapping("/api/manage/update/version/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        String versionCode = data.get("versionCode");
        String description = data.get("versionDescription");
        String url = data.get("versionUrl");
        return versionUpdateService.update(id, versionCode, description, url);
    }
}
