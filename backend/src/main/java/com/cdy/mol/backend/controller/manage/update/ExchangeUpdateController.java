package com.cdy.mol.backend.controller.manage.update;

import com.cdy.mol.backend.service.manage.update.ExchangeUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ExchangeUpdateController {
    @Autowired
    private ExchangeUpdateService exchangeUpdateService;
    @PostMapping("/api/manage/update/exchange/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        String title = data.get("title");
        String description = data.get("description");
        Integer score = Integer.parseInt(data.get("score"));
        String photo = data.get("photo");
        return exchangeUpdateService.update(id, title, description, score, photo);
    }
}
