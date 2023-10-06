package com.cdy.mol.backend.service.user.security;

import java.util.Map;

public interface FindPasswordService {
    Map<String, String> find_password(String code, String password, String confirmedPassword);
}
