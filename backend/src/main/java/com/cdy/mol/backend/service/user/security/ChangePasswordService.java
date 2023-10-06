package com.cdy.mol.backend.service.user.security;

import java.util.Map;

public interface ChangePasswordService {
    Map<String, String> change_password(String pwd, String password, String confirmedPassword);
}
