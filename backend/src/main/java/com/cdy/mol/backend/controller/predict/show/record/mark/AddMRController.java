package com.cdy.mol.backend.controller.predict.show.record.mark;

import com.cdy.mol.backend.service.predict.show.record.mark.AddMRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddMRController {
    @Autowired
    private AddMRService addMRService;

    @PostMapping("/api/predict/show/record/mark/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        String photo = data.get("photo");
        String mark = data.get("mark");
        String label = data.get("label");
        return addMRService.add(photo, mark, label);
    }
}
