package io.github.onlineshop.security.jwt;

import io.github.onlineshop.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenLifetime}")
    private long lifetimeMs;

    private SecretKey signingKeyCache = null;

    public String generateToken(UserPrincipal user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();

        claims.put("roles", rolesList);
        claims.put("id", user.getId().toString());
        claims.put("email", user.getEmail());

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifetimeMs);

        return Jwts.builder()
            .claims(claims)
            .subject(user.getUsername())
            .issuedAt(issuedDate)
            .expiration(expiredDate)
            .signWith(getSigningKey())
            .compact();
    }

    private SecretKey getSigningKey() {
        if(signingKeyCache == null)
            signingKeyCache = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        return signingKeyCache;
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getEmail(String token) {
        return getAllClaimsFromToken(token).get("email").toString();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
