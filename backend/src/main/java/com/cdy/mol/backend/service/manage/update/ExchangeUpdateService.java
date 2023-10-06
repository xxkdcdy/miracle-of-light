package com.cdy.mol.backend.service.manage.update;

import java.util.Map;

public interface ExchangeUpdateService {
    Map<String, String> update(Integer id, String title, String description, Integer score, String photo);
}
