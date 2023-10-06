package com.cdy.mol.backend.controller.user.score.exchange;

import com.cdy.mol.backend.service.user.score.exchange.InsertECLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InsertECLogController {
    @Autowired
    private InsertECLogService insertECLogService;
    @PostMapping("/api/user/score/exchange/buy/")
    public Map<String, String> buy(@RequestParam Map<String, String> data) {
        Integer exchangeId = Integer.parseInt(data.get("exchangeId"));
        return insertECLogService.insert(exchangeId);
    }
}
