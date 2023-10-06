package com.cdy.mol.backend.controller.manage.insert;

import com.cdy.mol.backend.service.manage.insert.ExchangeInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ExchangeInsertController {
    @Autowired
    private ExchangeInsertService exchangeInsertService;
    @PostMapping("/api/manage/insert/exchange/")
    public Map<String, String> insert(@RequestParam Map<String, String> data) {
        String title = data.get("title");
        String description = data.get("description");
        Integer score = Integer.parseInt(data.get("score"));
        String photo = data.get("photo");
        return exchangeInsertService.insert(title, description, score, photo);
    }
}
