package com.cdy.mol.backend.controller.predict.show.record.classify;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.predict.show.record.classify.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetListController {
    @Autowired
    private GetListService getListService;
    @GetMapping("/api/predict/show/record/classify/getlist/")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getListService.getList(page);
    }
}
