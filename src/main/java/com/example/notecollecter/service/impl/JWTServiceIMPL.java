package com.example.notecollecter.service.impl;

import com.example.notecollecter.service.JWTService;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTServiceIMPL implements JWTService {
    @Override
    public String extractUsername(String token) {
        return "";
    }

    @Override
    public String generateToken(UserDetails user) {
        return "";
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public String refreshToken(String prevToken) {
        return "";
    }
}
