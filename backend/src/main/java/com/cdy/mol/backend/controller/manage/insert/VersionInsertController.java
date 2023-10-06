package com.cdy.mol.backend.controller.manage.insert;

import com.cdy.mol.backend.service.manage.insert.VersionInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VersionInsertController {
    @Autowired
    private VersionInsertService versionInsertService;
    @PostMapping("/api/manage/insert/version/")
    public Map<String, String> insert(@RequestParam Map<String, String> data) {
        String versionCode = data.get("versionCode");
        String versionDescription = data.get("versionDescription");
        String versionUrl = data.get("versionUrl");
        return versionInsertService.insert(versionCode, versionDescription, versionUrl);
    }
}
