package com.cdy.mol.backend.controller.predict.data;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.predict.data.GetAllPDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAllPDController {
    @Autowired
    private GetAllPDService getAllPDService;
    @GetMapping("/api/predict/data/getall/")
    public JSONObject getAll() {
        return getAllPDService.getAll();
    }
}
