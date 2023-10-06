package com.cdy.mol.backend.controller.user.score;

import com.alibaba.fastjson.JSONObject;
import com.cdy.mol.backend.service.user.score.ScoreRanklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ScoreRanklistController {
    @Autowired
    private ScoreRanklistService scoreRanklistService;
    @GetMapping("/api/user/score/ranklist/")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return scoreRanklistService.getList(page);
    }
}
