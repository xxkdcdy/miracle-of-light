package com.cdy.mol.backend.service.manage.update;

import java.util.Map;

public interface InsectUpdateService {
    Map<String, String> update(Integer id, String insectOrder, String family, String familyCode,
                               String genus, String genusCode, String name, String nameCode,
                               String nameLatin, String environment, String region,
                               String suggest, String photo);
}
