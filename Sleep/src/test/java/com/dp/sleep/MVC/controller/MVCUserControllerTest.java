package com.dp.sleep.MVC.controller;

import com.dp.sleep.dto.UserDto;
import com.dp.sleep.dto.UserStopDto;
import com.dp.sleep.dto.UserSubscriptionDto;
import com.dp.sleep.dto.UserTimeDto;
import com.dp.sleep.mapper.UserMapper;
import com.dp.sleep.model.ConsumableEquipment;
import com.dp.sleep.model.Role;
import com.dp.sleep.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;


import java.time.LocalDate;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MVCUserControllerTest extends CommonTestMVC{

    @Autowired
    UserService service;

    @Autowired
    UserMapper mapper;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void registration() throws Exception {
        mvc.perform(get("/users/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)

                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("registration"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void testRegistration() throws Exception {
        UserDto userDto = new UserDto("denis3", "123", "d@yandex.ru", LocalDate.now(), "Den", "Pap", "Alex",
                "89992341234", "Russia", "Moscow", false, new Role(), new HashSet<>(), new HashSet<>(), null);
        mvc.perform(post("/users/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", userDto)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void getAll() throws Exception{
        mvc.perform(get("/users")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("users/viewAllUsers"))
                .andExpect(model().attributeExists("users"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void viewProfile() throws Exception {
        mvc.perform(get("/users/profile/{id}", "4")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profile/viewProfile"))
                .andExpect(model().attributeExists("user"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void updateProfile() throws Exception {
        mvc.perform(get("/users/profile/update/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profile/updateProfile"))
                .andExpect(model().attributeExists("user"))
                .andReturn();

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void testUpdateProfile() throws Exception {

        UserDto userDto = mapper.toDto(service.getOne(4L));
        userDto.setFirstName("Den3");
        mvc.perform(post("/users/profile/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", userDto)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/profile/" + userDto.getId()));


    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void rememberPassword() throws Exception {
        mvc.perform(get("/users/remember-password")

                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("users/rememberPassword"))
                .andReturn();

    }

//    @Test
//    //MailAuth failed
//    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
//    void testRememberPassword() throws Exception {
//        UserDto userDto = mapper.toDto(service.getOne(10L));
//        userDto.setPassword("password");
//        mvc.perform(post("/users/remember-password")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .flashAttr("changePasswordForm", userDto)
//                        .with(csrf())
//                )
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/login"));
//    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
//    void changePassword() throws Exception {
//        mvc.perform(get("/users/change-password")
//                        .param("uuid", "111")
//
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                )
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(model().attributeExists("uuid"))
//                .andExpect(view().name("users/changePassword"))
//                .andReturn();
//
//    }
//
//    @Test
//    void testChangePassword() throws Exception {
//        UserDto userDto = mapper.toDto(service.getOne(452L));
//        userDto.setPassword("password");
//        mvc.perform(post("/users/change-password")
//                        .param("uuid", "111")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .flashAttr("changePasswordForm", userDto)
//                        .with(csrf())
//                )
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/login"));
//    }

    @Test
    @WithMockUser(username = "denis", roles = "SUPER", password = "123")
    void getSubscription() throws Exception {
        mvc.perform(get("/users/subscription/{userId}", "4")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profile/viewSubscription"))
                .andExpect(model().attributeExists("user"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "denis3", roles = "USER", password = "123")
    void testGetSubscription() throws Exception {
        UserSubscriptionDto userSubscriptionDto = new UserSubscriptionDto();
        userSubscriptionDto.setUserId(10L);

        mvc.perform(post("/users/subscription")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("publishForm", userSubscriptionDto)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @WithMockUser(username = "denis3", roles = "USER", password = "123")
    void start() throws Exception {
        mvc.perform(get("/users/start/{id}", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profile/viewProgram"))
                .andExpect(model().attributeExists("user"))
                .andReturn();
    }


    @Test
    @WithMockUser(username = "denis3", roles = "USER", password = "123")
    void testStart() throws Exception {
        UserTimeDto userTimeDto = new UserTimeDto();

        mvc.perform(post("/users/start")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("publishForm", userTimeDto)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/start/" + userTimeDto.getUserId()));
    }

    @Test
    @WithMockUser(username = "denis3", roles = "USER", password = "123")
    void stop() throws Exception {
        UserStopDto userStopDto = new UserStopDto();

        mvc.perform(post("/users/stop")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("publishForm", userStopDto)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/history/" + userStopDto.getUserId()));
    }
}