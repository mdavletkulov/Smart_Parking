package com.example.smartParking.controllers;

import com.example.smartParking.Utils.ControllerUtils;
import com.example.smartParking.domain.Role;
import com.example.smartParking.domain.User;
import com.example.smartParking.domain.dto.CaptchaResponseDto;
import com.example.smartParking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        model.addAttribute("roles", Role.values());
        String url = String.format(CAPTCHA_URL, recaptchaSecret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }

        if (user.getPassword2() == null || user.getPassword2().isEmpty()) {
            model.addAttribute("password2Error", "Password confirmation is empty");
            bindingResult.addError(new ObjectError("password2Error", "Password confirmation is empty"));
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Passwords are different");
            bindingResult.addError(new ObjectError("passwordError", "Passwords are different"));
        }

        if (user.getFirstName().matches(".*\\d.*")) {
            model.addAttribute("firstNameError", "Имя не должно содержать цифр");
            bindingResult.addError(new ObjectError("firstNameError", "Имя не должно содержать цифр"));
        }
        if (user.getSecondName().matches(".*\\d.*")) {
            model.addAttribute("secondNameError", "Фамилия не должно содержать цифр");
            bindingResult.addError(new ObjectError("secondNameError", "Фамилия не должно содержать цифр"));
        }
        if (user.getMiddleName().matches(".*\\d.*")) {
            model.addAttribute("middleNameError", "Отчество не должно содержать цифр");
            bindingResult.addError(new ObjectError("middleNameError", "Отчество не должно содержать цифр"));
        }

        if (bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code in not found");
        }

        return "login";
    }
}
