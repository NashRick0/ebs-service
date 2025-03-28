package com.utd.ti.soa.ebs_service.utils;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;


public class Auth {
private final String SECRET_KEY = "aJksd9QzPl+sVdK7vYc/L4dK8HgQmPpQ5K9yApUsj3w";

public boolean validationToken(String token) {
    try {
        System.out.println("Token recibido para validación: " + token);
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        System.out.println("Token valido: " + claims.getSubject());
        return true;
    } catch (Exception e) {
        System.out.println("Token invalido: " + e.getMessage());
        return false;
    }
}
}
