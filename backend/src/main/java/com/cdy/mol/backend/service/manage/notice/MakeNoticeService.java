package com.cdy.mol.backend.service.manage.notice;

import java.util.Map;

public interface MakeNoticeService {
    Map<String, String> notice(Integer userId, String content);
}
