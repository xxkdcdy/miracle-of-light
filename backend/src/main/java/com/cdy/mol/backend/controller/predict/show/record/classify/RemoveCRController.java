package com.cdy.mol.backend.controller.predict.show.record.classify;

import com.cdy.mol.backend.service.predict.show.record.classify.RemoveCRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveCRController {
    @Autowired
    private RemoveCRService removeService;
    @PostMapping("/api/predict/show/record/classify/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        return removeService.remove(id);
    }
}
