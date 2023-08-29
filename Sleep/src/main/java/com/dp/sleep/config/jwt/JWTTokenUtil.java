package com.dp.sleep.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JWTTokenUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  //7 * 24 * 60 * 60 * 1000 = 1 неделя в миллисекундах
  public static final long JWT_TOKEN_VALIDITY = 604800000;

  //секрет для формирования подписи токена
  private final String secret = "zdtlD3JK56m6wTTgsNFhqzjqP";

  public String getUsernameFromToken(String token) {
    String subject = getClaimsFromToken(token, Claims::getSubject);
    JsonNode subjectJSON = null;
    try {
      subjectJSON = OBJECT_MAPPER.readTree(subject);
    } catch (JsonProcessingException e) {
      log.error("JWTTokenUtil#getUsernameFromToken(): {}", e.getMessage());
    }

    if (subjectJSON != null) {
      return subjectJSON.get("username").asText();
    } else {
      return null;
    }
  }

  //получение фиксированной информации из токена
  private <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimResolver.apply(claims);
  }

  //для получения любой информации из токена, необходим секретный ключ
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  //Подтверждение токена
  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  //Проверка, истекло ли время действия токена
  private boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  private Date getExpirationDateFromToken(String token) {
    return getClaimsFromToken(token, Claims::getExpiration);
  }

  //Создаем токен и кладем в него информацию о пользователе в виде .toString нашего CustomUserDetails
  public String generateToken(UserDetails userDetails) {
    return doGenerateToken(userDetails.toString());
  }

  //Настраиваем токен
  private String doGenerateToken(String subject) {
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }


}
