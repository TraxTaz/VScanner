package com.example.vscanner.Config.Token.Impl;

import com.example.vscanner.Config.Token.AccessToken;
import com.example.vscanner.Config.Token.AccessTokenDecoder;
import com.example.vscanner.Config.Token.AccessTokenEncoder;
import com.example.vscanner.Config.Token.Exception.InvalidAccessTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenDecoderAndEncoderImpl implements AccessTokenDecoder, AccessTokenEncoder {
    private final Key key;

    public AccessTokenDecoderAndEncoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (accessToken.getRole() != null) {
            claimsMap.put("role", accessToken.getRole());
            claimsMap.put("username", accessToken.getUsername());
        }
        if (accessToken.getUserID() != null) {
            claimsMap.put("userID", accessToken.getUserID());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(7, ChronoUnit.DAYS)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessTokenEncoded);
            Claims claims = jwt.getBody();

            String role = claims.get("role", String.class);
            Long studentId = claims.get("userID", Long.class);

            return new AccessTokenImpl(claims.getSubject(), studentId, role);
        }
        catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }
}
