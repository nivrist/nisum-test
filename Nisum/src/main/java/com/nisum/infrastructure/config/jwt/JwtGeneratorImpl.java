package com.nisum.infrastructure.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtGeneratorImpl implements JwtGenerator{
    @Override
    public String generateToken(String user) {
        String jwtToken="";
        jwtToken = Jwts.builder().setSubject(user).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secret").compact();
        return jwtToken;
    }
}