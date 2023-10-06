package com.cdy.mol.backend.controller.predict.data;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.predict.data.GetListPDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetListPDController {
    @Autowired
    private GetListPDService getListPDService;
    @GetMapping("/api/predict/data/getlist/")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getListPDService.getList(page);
    }
}
