package com.cdy.mol.backend.controller.manage.delete;

import com.cdy.mol.backend.service.manage.delete.DeleteVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteVersionController {
    @Autowired
    private DeleteVersionService deleteVersionService;
    @PostMapping("/api/manage/delete/version/")
    public Map<String, String> delete(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        return deleteVersionService.delete(id);
    }
}
