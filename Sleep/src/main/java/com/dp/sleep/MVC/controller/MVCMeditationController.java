package com.dp.sleep.MVC.controller;

import com.dp.sleep.dto.MeditationDto;
import com.dp.sleep.dto.MeditationSearchDto;
import com.dp.sleep.mapper.MeditationMapper;
import com.dp.sleep.mapper.UserMapper;
import com.dp.sleep.model.Meditation;
import com.dp.sleep.service.MeditationService;
import com.dp.sleep.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/meditations")
public class MVCMeditationController {

    private final MeditationMapper mapper;
    private final MeditationService service;

    public MVCMeditationController(MeditationMapper mapper, MeditationService service) {
        this.mapper = mapper;
        this.service = service;

    }

    @GetMapping("")
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        Page<Meditation> meditationPage = service.listAll(pageRequest);
        model.addAttribute("meditations", meditationPage);
        return "meditation/viewAllMeditations";
    }

    @GetMapping("/add")
    public String addMeditation() {
        return "meditation/addMeditation";
    }

    @PostMapping("/add")
    public String addMeditation(@ModelAttribute("meditationForm") MeditationDto meditationDto, @RequestParam MultipartFile file) throws IOException {
        if(file != null && file.getSize() > 0) {
            service.create(mapper.toEntity(meditationDto), file);
        } else {
            service.create(mapper.toEntity(meditationDto));
        }
        return "redirect:/meditations";
    }

    @GetMapping("/{meditationId}")
    public String viewOneMeditation(@PathVariable Long meditationId, Model model) {
        model.addAttribute("meditation", mapper.toDto(service.getOne(meditationId)));
        return "meditation/viewMeditation";
    }

    @GetMapping("/delete/{meditationId}")
    public String delete(@PathVariable Long meditationId) {
        service.softDelete(meditationId);
        return "redirect:/meditations";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        service.restore(id);
        return "redirect:/meditations";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("meditation", mapper.toDto(service.getOne(id)));
        return "meditation/updateMeditation";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("meditationForm") MeditationDto meditationDto,  @RequestParam MultipartFile file) throws IOException {
        if(file != null && file.getSize() > 0) {
            service.update(mapper.toEntity(meditationDto), file);
        } else {
            service.update(mapper.toEntity(meditationDto));
        }
        return "redirect:/meditations";
    }

    @PostMapping("/search")
    public String searchMeditations(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @ModelAttribute("meditationSearchForm") MeditationSearchDto meditationSearchDto,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "name"));
        model.addAttribute("meditations", service.findMeditations(meditationSearchDto, pageRequest));
        return "meditation/viewAllMeditations";
    }

    @GetMapping(value = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadMeditation(@Param(value = "meditationId") Long meditationId) throws IOException {
        Meditation meditation = service.getOne(meditationId);
        Path path = Paths.get(meditation.getFileName());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok().headers(headers(path.getFileName().toString()))
                .contentLength(path.toFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private HttpHeaders headers(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }


}
