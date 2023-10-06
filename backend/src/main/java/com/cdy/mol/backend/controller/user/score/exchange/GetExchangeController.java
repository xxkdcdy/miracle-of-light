package com.cdy.mol.backend.controller.user.score.exchange;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.user.score.exchange.GetExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetExchangeController {
    @Autowired
    private GetExchangeService getExchangeService;
    @GetMapping("/api/user/score/exchange/getlist/")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getExchangeService.getList(page);
    }
}
