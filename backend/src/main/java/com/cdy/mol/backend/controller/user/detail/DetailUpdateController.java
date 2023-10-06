package com.cdy.mol.backend.controller.user.detail;

import com.cdy.mol.backend.service.user.detail.DetailUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DetailUpdateController {
    @Autowired
    private DetailUpdateService detailUpdateService;
    @PostMapping("/api/user/detail/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return detailUpdateService.update(data);
    }
}
