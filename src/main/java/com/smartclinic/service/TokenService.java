package com.smartclinic.service;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.util.Date;
@Service public class TokenService {
 private final SecretKey key;
 public TokenService(@Value("${jwt.secret}") String secret){key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}

 // Токен действует сутки и содержит роль, чтобы контроллеры могли ограничивать доступ.
 public String generateToken(String email,String role){return Jwts.builder().subject(email).claim("role",role).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+86400000)).signWith(key).compact();}

 // Если подпись или срок действия неверны, библиотека выбросит исключение.
 public Claims validate(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token.replace("Bearer ","")).getPayload();}
}
