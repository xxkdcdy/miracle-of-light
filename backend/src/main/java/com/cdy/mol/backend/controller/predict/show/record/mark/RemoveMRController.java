package com.cdy.mol.backend.controller.predict.show.record.mark;

import com.cdy.mol.backend.service.predict.show.record.mark.RemoveMRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveMRController {
    @Autowired
    private RemoveMRService removeMRService;
    @PostMapping("/api/predict/show/record/mark/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        return removeMRService.remove(id);
    }
}
