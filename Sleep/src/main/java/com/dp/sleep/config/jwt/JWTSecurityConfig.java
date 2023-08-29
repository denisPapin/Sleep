//package com.dp.sleep.config.jwt;
//
//import com.dp.sleep.service.userdetails.CustomUserDetailsService;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.firewall.HttpFirewall;
//import org.springframework.security.web.firewall.StrictHttpFirewall;
//
//import java.util.Arrays;
//
//import static com.nv.sberschool.library.constants.SecurityConstants.*;
//import static com.nv.sberschool.library.constants.UserRolesConstants.ADMIN;
//import static com.nv.sberschool.library.constants.UserRolesConstants.LIBRARIAN;
//
////РЕСУРСЫ
////https://habr.com/ru/post/533868/
////https://habr.com/ru/post/340146/
////https://habr.com/ru/post/278411/
////https://habr.com/ru/post/545610/
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class JWTSecurityConfig {
//
//  private final JWTTokenFilter jwtTokenFilter;
//  private final CustomUserDetailsService customUserDetailsService;
//
//  public JWTSecurityConfig(JWTTokenFilter jwtTokenFilter, CustomUserDetailsService customUserDetailsService) {
//    this.jwtTokenFilter = jwtTokenFilter;
//    this.customUserDetailsService = customUserDetailsService;
//  }
//
//  //https://docs.spring.io/spring-security/reference/servlet/exploits/firewall.html
//  @Bean
//  public HttpFirewall httpFirewall() {
//    StrictHttpFirewall firewall = new StrictHttpFirewall();
//    firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//    return firewall;
//  }
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http
//        .cors(AbstractHttpConfigurer::disable)
//        .csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(auth -> auth
////            .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
////            .requestMatchers(REST.USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
////            .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
////            .requestMatchers(BOOKS_WHITE_LIST.toArray(String[]::new)).permitAll()
////            .requestMatchers(AUTHORS_WHITE_LIST.toArray(String[]::new)).permitAll()
////            .requestMatchers(REST.BOOKS_WHITE_LIST.toArray(String[]::new)).permitAll()
////            .requestMatchers(REST.AUTHORS_WHITE_LIST.toArray(String[]::new)).permitAll()
////            .requestMatchers(REST.AUTHORS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
////            .requestMatchers(AUTHORS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
////            .requestMatchers(BOOKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
////            .requestMatchers(REST.BOOKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
////            .requestMatchers(REST.USERS_PERMISSION_LIST.toArray(String[]::new)).hasRole()
//        )
//        .exceptionHandling()
//        .authenticationEntryPoint((request, response, authException) -> {
//          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//        })
//        .and()
//        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
//        .userDetailsService(customUserDetailsService)
//        .build();
//  }
//
//  @Bean
//  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//    return authenticationConfiguration.getAuthenticationManager();
//  }
//}
