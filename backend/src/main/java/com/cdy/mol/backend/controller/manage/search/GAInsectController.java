package com.cdy.mol.backend.controller.manage.search;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.manage.search.GAInsectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GAInsectController {
    @Autowired
    private GAInsectService gaInsectService;

    @GetMapping("/api/manage/search/insect/all/")
    public JSONObject getAll(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return gaInsectService.getAll(page);
    }
}
