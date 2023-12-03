package com.nisum.infrastructure.config.jwt;

import java.util.Map;

public interface JwtGenerator {
    String generateToken(String user);
}
