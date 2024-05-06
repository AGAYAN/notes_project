package org.example.project_notes.Token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.example.project_notes.enumType.TokenType;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtCore {
    private String secret;
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_TYPE = "tokenType";

    public String generateAccessToken(TokenType tokenType) {

        return JWT.create()
                .withSubject(AUTHORIZATION)
                .withClaim(TOKEN_TYPE, tokenType.name())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + tokenType.getTime()))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String generateRefreshToken(TokenType tokenType) {
        return JWT.create()
                .withSubject(AUTHORIZATION)
                .withClaim(TOKEN_TYPE, tokenType.name())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + tokenType.getTime()))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }
}
