package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.IRoleService;
import com.sandrajavaschool.OnlineStore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("User")
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping(value = "/list")
    public String list(Model model) {

        model.addAttribute("title", "Users List");

        List<User> users = userService.findAll();
        model.addAttribute("users", users);


        return "usersList";
    }

    @GetMapping(value = "/ver/{id}")
    public String view(@PathVariable(value = "id") Long id,
                       Map<String, Object> model,
                       RedirectAttributes flash) {

        User user = userService.findOne(id);

        if (user == null) {
            flash.addFlashAttribute("error", "The client does not exist");
            return "redirect:/form";
        }

        model.put("user", user);
        return "aqui indicamos el html";
    }
}
