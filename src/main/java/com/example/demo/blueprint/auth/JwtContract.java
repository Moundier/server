package com.example.demo.blueprint.auth;

import java.security.Key;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public abstract class JwtContract {

	public abstract String generateToken(Map<String, Object> extraClaims, UserDetails userDetails); // Generates a JWT with extra custom claims and user details.

	public abstract String generateAccessToken(UserDetails userDetails); // Generates a JWT based on the provided user details.

	public abstract String generateRefreshToken(UserDetails userDetails);

	public abstract boolean isTokenValid(String token, UserDetails userDetails); // Checks if a token is valid for the provided user details.

	public abstract boolean isTokenExpired(String token); // Checks if a token has expired.

	public abstract String extractUsername(String token); // Extracts the username from a token.

	public abstract <T> T extractSpecificClaim(String token, Function<Claims, T> claimsResolver); // Extracts a specific claim using a resolver function.

	public abstract Claims extractAllClaims(String token); // Extracts all claims from a token.

	public abstract Key getSignInKey(); // Gets the secret key used for token signing.
}
