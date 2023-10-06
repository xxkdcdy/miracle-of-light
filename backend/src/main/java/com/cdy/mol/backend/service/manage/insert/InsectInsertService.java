package com.cdy.mol.backend.service.manage.insert;

import java.util.Map;

public interface InsectInsertService {
    Map<String, String> insert(String insectOrder, String family, String familyCode,
                               String genus, String genusCode, String name, String nameCode,
                               String nameLatin, String environment, String region,
                               String suggest, String photo);
}
