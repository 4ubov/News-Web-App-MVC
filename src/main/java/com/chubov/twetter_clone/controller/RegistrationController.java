package com.chubov.twetter_clone.controller;

import com.chubov.twetter_clone.domain.User;
import com.chubov.twetter_clone.domain.dto.CaptchaResponseDto;
import com.chubov.twetter_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password_confirmation") String password_confirmation,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        //Captcha response
        String captcha_url_modified = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(
                captcha_url_modified,
                Collections.emptyList(),
                CaptchaResponseDto.class);
        if (!response.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }
        //Password validation/catching error
        boolean isConfirmEmpty = StringUtils.isEmpty(password_confirmation);
        if (isConfirmEmpty) {
            model.addAttribute("passwordConfirmationError", "Password confirmation cannot be empty");
        }
        if (user.getPassword() != null && !user.getPassword().equals(password_confirmation)) {
            model.addAttribute("passwordError", "Passwords not equal");
            return "registration";
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "registration";
        }

        /*Если пользователь найден в БД, то вывести User exist,
            иначе добавляем пользователя и редиректим на login page*/
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User already exists");
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

        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found.");
        }

        return "login";
    }
}
