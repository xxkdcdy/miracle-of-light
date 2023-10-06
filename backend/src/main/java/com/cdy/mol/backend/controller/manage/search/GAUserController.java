package com.cdy.mol.backend.controller.manage.search;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.manage.search.GAUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GAUserController {
    @Autowired
    private GAUserService gaUserService;

    @GetMapping("/api/manage/search/user/all/")
    public JSONObject getAll(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return gaUserService.getAll(page);
    }
}
