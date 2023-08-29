package com.dp.sleep.MVC.controller;

import com.dp.sleep.dto.ConvertedDataSearchDto;
import com.dp.sleep.dto.MeditationSearchDto;
import com.dp.sleep.model.Direction;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MVCConvertedDataControllerTest extends CommonTestMVC {

    @Test
    @WithMockUser(username = "denis_papin", roles = "SUPER", password = "123")
    void getAll() throws Exception {
        mvc.perform(get("/history")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("history/viewAllHistory"))
                .andExpect(model().attributeExists("convertedDates"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "denis_papin", roles = "SUPER", password = "123")
    void viewOneConvertedData() throws Exception {
        mvc.perform(get("/history/{convertedDataId}", "5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("/history/viewConvertedData"))
                .andExpect(model().attributeExists("convertedData"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "denis_papin", roles = "SUPER", password = "123")
    void searchConvertedDates() throws Exception{
        ConvertedDataSearchDto convertedDataSearchDto = new ConvertedDataSearchDto();
        convertedDataSearchDto.setDate(LocalDate.of(2023, 5, 2));
        mvc.perform(post("/history/search")
                        .param("page", "1")
                        .param("size", "5")
                        .flashAttr("convertedDataSearchForm", convertedDataSearchDto)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("convertedDates"))
                .andExpect(view().name("history/viewAllHistory"));

    }
}