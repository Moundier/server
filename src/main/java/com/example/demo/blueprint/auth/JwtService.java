package com.example.demo.blueprint.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entities.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService extends JwtContract {

  @SuppressWarnings("unused")
  private final long SET_30_SEC = (1000 * 30);

  @SuppressWarnings("used")
  private final long SET_01_MIN = (1000 * 60);

  @SuppressWarnings("unused")
  private final long SET_30_MIN = (1000 * 60 * 30);

  @SuppressWarnings("unused")
  private final long SET_01_HHR = (1000 * 60 * 60);

  @SuppressWarnings("unused")
  private final long SET_01_DAY = (1000 * 60 * 60 * 24);

  // TODO: remove in-memory secret key
  private static final String SECRET_KEY = "DFatenFSYbaa+PaCOygVv8JtOc3d1UPv2BCIIeQ2TwGTA2EuhNQpGhszoUEN2bFR";

  public String generateAccessToken(User userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, User userDetails) {
    return buildToken(extraClaims, userDetails, this.SET_01_MIN / 2);
  }

  public String generateRefreshToken(User userDetails) {
    return buildToken(new HashMap<>(), userDetails, this.SET_01_MIN * 10);
  }

  public String buildToken(Map<String, Object> extraClaims, User userDetails, long time) {
    try {
      return Jwts.builder()
      .setClaims(extraClaims)
      .setSubject(userDetails.getId().toString()) // Subject is unique
      .setIssuer("localhost:9090")
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + time))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();

    } catch (SignatureException | ExpiredJwtException e) {
      e.toString();
      return null;
    }
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  public boolean isTokenExpired(String token) {
    return extractSpecificClaim(token, Claims::getExpiration).before(new Date()); // Todo: checks if token is expired
  }

  public String extractUsername(String token) {
    try {
      return extractSpecificClaim(token, Claims::getSubject);
    } catch (Exception e) {
      System.out.println("FOUND 1");
      return null; // Todo: handle exception
    }
  }

  public Claims extractAllClaims(String token) {
    try {
      return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    } catch (ExpiredJwtException e) {
      return null;
    }
  }

  public <T> T extractSpecificClaim(String token, Function<Claims, T> claimsResolver) {
    try {
      final Claims claims = extractAllClaims(token); // ERROR: expired clause happens in here
      return claimsResolver.apply(claims);
    } catch (Exception e) {
      return null; // Todo: handle exception
    }
  }

  public Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
