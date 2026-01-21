package learnsmartly.ls_api.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import learnsmartly.ls_api.entity.LsUser;

/**
 * JWT utility service.
 *
 * Tokens carry:
 *  - subject: userId (as String)
 *  - claims: userId, role
 */
@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-minutes:120}")
    private long expirationMinutes;

    public String generateToken(LsUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole() != null ? user.getRole().name() : null);

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationMinutes * 60);

        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(user.getId()))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Long extractUserId(String token) {
        Claims claims = parseClaims(token);
        Object v = claims.get("userId");
        if (v instanceof Number n) return n.longValue();
        if (v != null) return Long.parseLong(v.toString());
        return Long.parseLong(claims.getSubject());
    }

    public String extractRole(String token) {
        Claims claims = parseClaims(token);
        Object v = claims.get("role");
        return v == null ? null : v.toString();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseClaims(token);
            Date exp = claims.getExpiration();
            return exp == null || exp.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
