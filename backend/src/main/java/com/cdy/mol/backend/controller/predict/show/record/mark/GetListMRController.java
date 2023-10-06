package com.cdy.mol.backend.controller.predict.show.record.mark;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.predict.show.record.mark.GetListMRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetListMRController {
    @Autowired
    private GetListMRService getListMRService;
    @GetMapping("/api/predict/show/record/mark/getlist/")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getListMRService.getList(page);
    }
}
