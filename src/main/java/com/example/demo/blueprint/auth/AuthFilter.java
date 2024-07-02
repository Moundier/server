package com.example.demo.blueprint.auth;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entities.user.User;
import com.example.demo.entities.user.UserRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
  
  private final UserRepo userRepo;
  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String subject;
    final String token;


    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      System.out.println("Filter chain: HttpHeaders.AUTHORIZATION not found");
      filterChain.doFilter(request, response);
      return;
    }

    token = authHeader.substring(7);
    subject = jwtService.extractSubject(token);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Todo: IsUserAuthenticated

    this.createReport(token, subject);

    if (subject != null && auth == null) {

      User user = userRepo.findById(Long.parseLong(subject)).get();

      if (jwtService.isTokenValid(token, user)) {

        Authz authz = new Authz(user,null,user.getAuthorities());

        authz.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authz);
      }
    }

    filterChain.doFilter(request, response);
  }

  void createReport(String token, String sub) {
    System.out.println("-".repeat(20) + "[Interceptor]" + "-".repeat(20));
    System.out.println(token);
    System.out.println(sub); 
  }

  private class Authz extends UsernamePasswordAuthenticationToken {

    public Authz(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
      super(principal, credentials, authorities);
    }
  }
}