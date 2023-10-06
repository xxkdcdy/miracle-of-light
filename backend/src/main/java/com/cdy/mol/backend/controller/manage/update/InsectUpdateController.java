package com.cdy.mol.backend.controller.manage.update;

import com.cdy.mol.backend.service.manage.update.InsectUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InsectUpdateController {
    @Autowired
    private InsectUpdateService insectUpdateService;
    @PostMapping("/api/manage/update/insect/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        String insectOrder = data.get("insectOrder");
        String family = data.get("family");
        String familyCode = data.get("familyCode");
        String genus = data.get("genus");
        String genusCode = data.get("genusCode");
        String name = data.get("name");
        String nameCode = data.get("nameCode");
        String nameLatin = data.get("nameLatin");
        String environment = data.get("environment");
        String region = data.get("region");
        String suggest = data.get("suggest");
        String photo = data.get("photo");
        return insectUpdateService.update(id, insectOrder, family, familyCode, genus, genusCode, name, nameCode,
                nameLatin, environment, region, suggest, photo);
    }
}
