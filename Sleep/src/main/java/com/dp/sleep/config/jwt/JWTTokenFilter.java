package com.dp.sleep.config.jwt;

import com.dp.sleep.service.userdetails.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {

  private final CustomUserDetailsService customUserDetailsService;
  private final JWTTokenUtil jwtTokenUtil;

  public JWTTokenFilter(CustomUserDetailsService customUserDetailsService, JWTTokenUtil jwtTokenUtil) {
    this.customUserDetailsService = customUserDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    String token = null;
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if(header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    //получаем JWT токен
    //Authorization: Bearer 123jhvzdfksahegfk1gh3vkhasvdkj#$@123mnbasljhgdasjhedlvuy2fe
    token = header.split(" ")[1].trim();
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
    if(!jwtTokenUtil.validateToken(token, userDetails)) {
      filterChain.doFilter(request, response);
      return;
    }

    //установка сущности пользователя в spring security context
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
