package com.cdy.mol.backend.controller.manage.update;

import com.cdy.mol.backend.service.manage.update.MarkUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MarkUpdateController {
    @Autowired
    private MarkUpdateService markUpdateService;
    @PostMapping("/api/manage/update/mark/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        String state = data.get("state");
        return markUpdateService.update(id, state);
    }
}
