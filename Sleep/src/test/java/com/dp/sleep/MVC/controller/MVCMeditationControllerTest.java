package com.dp.sleep.MVC.controller;

import com.dp.sleep.dto.MeditationDto;
import com.dp.sleep.dto.MeditationSearchDto;
import com.dp.sleep.mapper.MeditationMapper;
import com.dp.sleep.model.Direction;
import com.dp.sleep.model.Meditation;
import com.dp.sleep.service.MeditationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MVCMeditationControllerTest extends CommonTestMVC {

    @Autowired
    MeditationService service;

    @Autowired
    MeditationMapper mapper;

    @Test
    void getAll() throws Exception {
        mvc.perform(get("/meditations")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("meditation/viewAllMeditations"))
                .andExpect(model().attributeExists("meditations"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void addMeditation() throws Exception {
        mvc.perform(get("/meditations/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)

                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("meditation/addMeditation"))
                .andReturn();
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
//    void testAddMeditation() throws Exception {
//        MeditationDto meditationDto = new MeditationDto("Meditation_test", Direction.CALM, null);
//        MultipartFile multipartFile = null;
//        mvc.perform(post("/meditations/add")
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
//                        .flashAttr("file", multipartFile)
//                        .flashAttr("meditationForm", meditationDto)
//                        .with(csrf()))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/meditations"))
//                .andExpect(redirectedUrlTemplate("/meditations"));
//    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void viewOneMeditation() throws Exception {
        mvc.perform(get("/meditations/{meditationId}", "352")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("/meditation/viewMeditation"))
                .andExpect(model().attributeExists("meditation"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void delete() throws Exception {
        mvc.perform(get("/meditations/delete/{meditationId}", "352")

                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/meditations"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void restore() throws Exception {
        mvc.perform(get("/meditations/restore/{id}", "352")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/meditations"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void update() throws Exception {
        mvc.perform(get("/meditations/update/{id}", "352")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("meditation/updateMeditation"))
                .andExpect(model().attributeExists("meditation"))
                .andReturn();
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
//    void testUpdate() throws Exception {
//
//
//        MeditationDto meditationDto = mapper.toDto(service.getOne(352L));
//        meditationDto.setName("meditation_test");
//        mvc.perform(post("/meditations/update")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .flashAttr("meditationForm", meditationDto)
//                        .with(csrf())
//                )
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/meditations"))
//                .andExpect(redirectedUrl("/meditations"));
//
//    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    void searchMeditations() throws Exception{
        MeditationSearchDto meditationSearchDto = new MeditationSearchDto("Медитация3", Direction.CALM);
        mvc.perform(post("/meditations/search")
                        .param("page", "1")
                        .param("size", "5")
                        .flashAttr("meditationSearchForm", meditationSearchDto)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("meditations"))
                .andExpect(view().name("meditation/viewAllMeditations"));

    }

    @Test
    void downloadMeditation() {
    }
}