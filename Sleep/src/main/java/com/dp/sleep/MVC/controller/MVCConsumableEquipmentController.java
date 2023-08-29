package com.dp.sleep.MVC.controller;

import com.dp.sleep.mapper.ConsumableEquipmentMapper;
import com.dp.sleep.service.ConsumableEquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@RequestMapping("/equip")
//public class MVCConsumableEquipmentController {
//
//    private final ConsumableEquipmentService service;
//    private final ConsumableEquipmentMapper mapper;
//
//    public MVCConsumableEquipmentController(ConsumableEquipmentService service, ConsumableEquipmentMapper mapper) {
//        this.service = service;
//        this.mapper = mapper;
//    }
//
//    @GetMapping("/check/{userId}")
//    public String getSubscription(@PathVariable Long userId, Model model) {
//        model.addAttribute("user", mapper.toDto(service.findByUserId(userId)));
//        return "profile/viewSubscription";
//    }
//}
