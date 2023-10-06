package com.cdy.mol.backend.controller.manage.notice;

import com.cdy.mol.backend.service.manage.notice.MakeNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MakeNoticeController {
    @Autowired
    private MakeNoticeService makeNoticeService;
    @PostMapping("/api/manage/notice/make/")
    public Map<String, String> notice(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("userId"));
        String content = data.get("content");
        return makeNoticeService.notice(userId, content);
    }
}
