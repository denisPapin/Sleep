package com.dp.sleep.MVC.controller;


import com.dp.sleep.dto.UserDto;
import com.dp.sleep.dto.UserStopDto;
import com.dp.sleep.dto.UserSubscriptionDto;
import com.dp.sleep.dto.UserTimeDto;
import com.dp.sleep.mapper.UserMapper;
import com.dp.sleep.model.User;
import com.dp.sleep.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.dp.sleep.constants.UserRolesConstants.ADMIN;


@Controller
@RequestMapping("/users")
public class MVCUserController {

  private final UserService service;
  private final UserMapper mapper;

  public MVCUserController(UserService service, UserMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping("/registration")
  public String registration() {
    return "registration";
  }


  @PostMapping("/registration")
  public String registration(@ModelAttribute("userForm") UserDto userDto, BindingResult bindingResult) {
    if (userDto.getLogin().equals(ADMIN) || service.getUserByLogin(userDto.getLogin()) != null) {
      bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
      return "redirect:/login";
    }
    if (service.getUserByEmail(userDto.getEmail()) != null) {
      bindingResult.rejectValue("email", "error.email", "Такая почта уже существует");
      return "redirect:/login";
    }
    service.create(mapper.toEntity(userDto));
    return "redirect:/login";
  }

  @GetMapping("")
  public String getAll(
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "5") int pageSize,
      Model model
  ) {
    PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Direction.ASC, "login"));
    Page<User> userPage = service.listAll(pageRequest);
    Page<UserDto> userDtoPage = new PageImpl<>(mapper.toDtos(userPage.getContent()), pageRequest, userPage.getTotalElements());
    model.addAttribute("users", userDtoPage);
    return "users/viewAllUsers";
  }

  @GetMapping("/profile/{id}")
  public String viewProfile(@PathVariable Long id, Model model) {
    model.addAttribute("user", mapper.toDto(service.getOne(id)));
    return "profile/viewProfile";
  }

  @GetMapping("/profile/update/{id}")
  public String updateProfile(@PathVariable Long id, Model model) {
    model.addAttribute("user", mapper.toDto(service.getOne(id)));
    return "profile/updateProfile";
  }

  @PostMapping("/profile/update")
  public String updateProfile(@ModelAttribute("userForm") UserDto userDto) {
    service.update(mapper.toEntity(userDto));
    return "redirect:/users/profile/" + userDto.getId();
  }

  @GetMapping("remember-password")
  public String rememberPassword() {
    return "users/rememberPassword";
  }

  @PostMapping("remember-password")
  public String rememberPassword(@ModelAttribute("changePasswordForm") UserDto userDto) {
    service.sendChangePasswordEmail(service.getUserByEmail(userDto.getEmail()));
    return "redirect:/login";
  }

  @GetMapping("/change-password")
  public String changePassword(@PathParam(value = "uuid") String uuid, Model model) {
    model.addAttribute("uuid", uuid);
    return "users/changePassword";
  }

  @PostMapping("change-password")
  public String changePassword(
          @PathParam(value = "uuid") String uuid,
          @ModelAttribute("changePasswordForm") UserDto userDto
  ) {
    service.changePassword(uuid, userDto.getPassword());
    return "redirect:/login";
  }

  @GetMapping("/subscription/{userId}")
  public String getSubscription(@PathVariable Long userId, Model model) {
    model.addAttribute("user", mapper.toDto(service.getOne(userId)));
    return "profile/viewSubscription";
  }

  @PostMapping("/subscription")
  public String getSubscription(@ModelAttribute("publishForm") UserSubscriptionDto userSubscriptionDto) {
    //CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    userSubscriptionDto.setUserId(service.getUserByLogin(name).getId());
    service.setSub(userSubscriptionDto);
    return "redirect:/";

  }

  @GetMapping("/start/{id}")
  public String start(@PathVariable Long id, Model model) {
    model.addAttribute("user", mapper.toDto(service.getOne(id)));
    return "profile/viewProgram";
  }

  @PostMapping("/start")
  public String start(@ModelAttribute("publishForm")UserTimeDto userTimeDto) {
    //CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    userTimeDto.setUserId(service.getUserByLogin(name).getId());
    service.setStartTime(userTimeDto);
    return "redirect:/users/start/" +userTimeDto.getUserId();
  }



  @PostMapping("/stop")
  public String stop(@ModelAttribute("publishForm") UserStopDto userStopDto) {
    //CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String name = authentication.getName();
    userStopDto.setUserId(service.getUserByLogin(name).getId());
    service.stop(userStopDto);
    return "redirect:/history/" + userStopDto.getUserId() ;

  }



}


