package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.IRoleService;
import com.sandrajavaschool.OnlineStore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("User")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping(value = "/list")
    public String list(Model model) {

        model.addAttribute("title", "Users List");

        List<User> users = userService.findAll();
        model.addAttribute("users", users);


        return "user/usersList";
    }

    @GetMapping(value = "/view/{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       Map<String, Object> model,
                       RedirectAttributes flash) {

        model.put("title", "Profile");

        User user = userService.findOne(id);

        if (user == null) {
            flash.addFlashAttribute("error", "The client does not exist");
            return "redirect:/usersList";
        }

        model.put("user", user);

        return "user/profile";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {

        model.addAttribute("title", "Sign Up!");

        User user = new User();
        model.addAttribute("user", user);

        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);

        return "user/signup";
    }

    @PostMapping(value = "/update/{id}")
    public String save(@ModelAttribute User user,
                         RedirectAttributes flash,
                         SessionStatus status) {





        String flashmessage = "Congratulation! You have an account";
        userService.save(user);

        flash.addFlashAttribute("success", flashmessage);
        return "redirect:/list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes flash) {

        if (id > 0) {
            userService.delete(id);
            flash.addFlashAttribute("success", "The user has been deleted");
        }

        return "redirect:/list";
    }


}
