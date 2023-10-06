package com.cdy.mol.backend.service.manage.update;

import java.util.Map;

public interface UserUpdateService {
    Map<String, String> update(Integer id, Integer flag, String nickname, String name, String address, String description, String email, String organization);
}
