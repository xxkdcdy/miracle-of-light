package com.cdy.mol.backend.controller.manage.update;

import com.cdy.mol.backend.service.manage.update.ScoreUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ScoreUpdateController {
    @Autowired
    private ScoreUpdateService scoreUpdateService;
    @PostMapping("/api/manage/update/score/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("userId"));
        Integer score = Integer.parseInt(data.get("score"));
        return scoreUpdateService.update(userId, score);
    }
}
