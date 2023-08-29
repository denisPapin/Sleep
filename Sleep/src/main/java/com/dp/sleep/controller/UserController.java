package com.dp.sleep.controller;

import com.dp.sleep.config.jwt.JWTTokenUtil;
import com.dp.sleep.dto.LoginDto;
import com.dp.sleep.dto.UserDto;
import com.dp.sleep.mapper.UserMapper;
import com.dp.sleep.model.User;
import com.dp.sleep.service.UserService;
import com.dp.sleep.service.userdetails.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest/users")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController extends GenericController<User, UserDto> {


    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final UserService service;

    public UserController(
            UserService service,
            UserMapper mapper,
            CustomUserDetailsService customUserDetailsService,
            JWTTokenUtil jwtTokenUtil
    ) {
        super(service, mapper);
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.service = service;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) {
        Map<String, Object> response = new HashMap<>();
        UserDetails foundUser = customUserDetailsService.loadUserByUsername(loginDto.getLogin());
        if(!service.checkPassword(loginDto.getPassword(), foundUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка авторизации!\n Неверный пароль!");
        }

        String token = jwtTokenUtil.generateToken(foundUser);
        response.put("token", token);
        response.put("username", foundUser.getUsername());
        response.put("authorities", foundUser.getAuthorities());
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping("/example/{id}")  @GetMapping("/example/id=1")
//    public void exampleMethod(@PathVariable("id") int id, @PathParam("name") int id) {
//        // метод тело
//    }
}
