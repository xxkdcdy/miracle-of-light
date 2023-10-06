package com.cdy.mol.backend.controller.version;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.version.LatestVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LatestVersionController {
    @Autowired
    private LatestVersionService latestVersionService;
    @GetMapping("/api/version/latest/")
    public JSONObject latestVersion() {
        return latestVersionService.latestVersion();
    }
}
