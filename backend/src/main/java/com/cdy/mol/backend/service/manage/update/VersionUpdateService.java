package com.cdy.mol.backend.service.manage.update;

import java.util.Map;

public interface VersionUpdateService {
    Map<String, String> update(Integer id, String versionCode, String description, String url);
}
