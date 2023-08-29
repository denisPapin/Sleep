package com.dp.sleep.MVC.controller;

import com.dp.sleep.dto.ConvertedDataSearchDto;
import com.dp.sleep.mapper.ConvertedDataMapper;
import com.dp.sleep.mapper.UserMapper;
import com.dp.sleep.model.ConvertedData;
import com.dp.sleep.service.ConvertedDataService;
import com.dp.sleep.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/history")
public class MVCConvertedDataController {

    private final ConvertedDataService service;
    private final ConvertedDataMapper mapper;
//    private final UserService userService;
//    private final UserMapper userMapper;

    public MVCConvertedDataController(ConvertedDataService service, ConvertedDataMapper mapper) {
        this.service = service;
        this.mapper = mapper;
//        this.userService = userService;
//        this.userMapper = userMapper;
    }

    @GetMapping("")
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "sleepDuration"));
        Page<ConvertedData> convertedDataPage = service.listAll(pageRequest);
        model.addAttribute("convertedDates", convertedDataPage);
        return "history/viewAllHistory";
    }

    @GetMapping("/{userConvertedDataId}")
    public String viewOneConvertedData(@PathVariable Long userConvertedDataId,  Model model) {
        model.addAttribute("convertedData", mapper.toDto(service.findByUserIdAndDate(userConvertedDataId)));
//        model.addAttribute("user", userMapper.toDto(userService.getOne(userConvertedDataId)));
        return "/history/viewConvertedData";
    }

    @PostMapping("/search")
    public String searchConvertedDates(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @ModelAttribute("convertedDataSearchForm") ConvertedDataSearchDto convertedDataSearchDto,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "date"));
        model.addAttribute("convertedDates", service.findSleep(convertedDataSearchDto, pageRequest));
        return "history/viewAllHistory";
    }




}
