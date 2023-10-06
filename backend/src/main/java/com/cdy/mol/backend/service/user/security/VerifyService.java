package com.cdy.mol.backend.service.user.security;

import java.util.Map;

public interface VerifyService {
    Map<String, String> verify_email(String code);
    Map<String, String> verify_ID(String idCard, String name);
}
