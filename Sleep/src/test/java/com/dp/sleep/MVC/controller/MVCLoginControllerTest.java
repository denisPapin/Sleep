package com.dp.sleep.MVC.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MVCLoginControllerTest extends CommonTestMVC {

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void login() throws Exception{
        mvc.perform(get("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrlTemplate("/"));
    }
}