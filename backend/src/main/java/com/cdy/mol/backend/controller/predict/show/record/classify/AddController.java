package com.cdy.mol.backend.controller.predict.show.record.classify;

import com.cdy.mol.backend.service.predict.show.record.classify.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddController {
    @Autowired
    private AddService addService;
    @PostMapping("/api/predict/show/record/classify/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        String classify = data.get("classify");
        return addService.add(classify);
    }
}
