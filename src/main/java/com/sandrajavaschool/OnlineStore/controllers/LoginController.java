
package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.security.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/api")
public class LoginController {

    private LoginDto loginDto;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model,
                        Principal principal,
                        RedirectAttributes flash) {

        if (principal != null) {
            //si es asi es pq ya ha iniciado sesion anteriormente
            //asi evitamos que haga doble inicio de sesion
            flash.addFlashAttribute("info", "You're already logged in");
            return "redirect:/";
        }

        if (error != null) {
            flash.addFlashAttribute("error", "The username or password you entered is incorrect. Please double-check and try again.");
        }

        if(logout != null) {
            flash.addFlashAttribute("success", "You have logged out successfully");
        }

        model.addAttribute("loginDto", new LoginDto());

        return "login";
    }
}

