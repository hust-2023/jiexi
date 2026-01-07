package com.example.jiexi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "12345678901234567890123456789012"; // ≥32位
    private static final long EXPIRATION = 1000L * 60 * 60 * 24; // 1天

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * ✅ 生成 Token（包含 userId + username）
     */
    public String generateToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * ✅ 从 Token 中解析 userId
     */
    public Long getUserIdFromToken(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return body.get("userId", Long.class);
        } catch (JwtException e) {
            return null;   // token无效或过期
        }
    }

    /**
     * ✅ 从请求头中解析 userId（给 Controller 直接使用）
     */
    public Long getUserIdFromRequest(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return null;
        }

        String token = auth.substring(7);
        return getUserIdFromToken(token);
    }
}
