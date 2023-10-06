package com.cdy.mol.backend.service.manage.insert;

import java.util.Map;

public interface VersionInsertService {
    Map<String, String> insert(String versionCode, String versionDescription, String versionUrl);
}
