package com.cdy.mol.backend.service.manage.update;

import java.util.Map;

public interface ScoreUpdateService {
    Map<String, String> update(Integer userId, Integer score);
}
