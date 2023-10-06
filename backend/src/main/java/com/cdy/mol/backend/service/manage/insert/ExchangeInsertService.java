package com.cdy.mol.backend.service.manage.insert;

import java.util.Map;

public interface ExchangeInsertService {
    Map<String, String> insert(String title, String description, Integer score, String photo);
}
