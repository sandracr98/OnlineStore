package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.paginator.PageRender;
import com.sandrajavaschool.OnlineStore.service.implService.IClientAddressService;
import com.sandrajavaschool.OnlineStore.service.implService.IRoleService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("User")
@RequiredArgsConstructor
public class UserController {

    //A esto se le llama inyeccion del constructor
    //Esto es mejor inyectarlo asi, poniendo el final y añadiendo
    //un constructor con @Autowired ¿Por qué?

    //1.-Las dependencias están claramente identificadas, así no se te olvida
    // cuando estemos testeando o instanciarlo en alguna otra clase

    //2.- Las dependencias pueden ser 'final' lo cual ayuda con la robustez
    // y la seguridad

    //3.- Puedes crear mocks por ti mismo e inyectarlos simplemente llamando al constructor.
    //no necesitas establecer las dependencias con los mocks


    final private IUserService userService;
    final private IRoleService roleService;
    final private IClientAddressService clientAddressService;

    //@Autowired
    //public UserController(IUserService userService, IRoleService roleService, IClientAddressService clientAddressService) {
      //  this.userService = userService;
        //this.roleService = roleService;
        //this.clientAddressService = clientAddressService;
    //}

    @GetMapping(value = "/mainPage")
    public String mainPage() {
        return "/mainPAge";
    }

    @GetMapping(value = "/list")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page,
                       Model model) {

        model.addAttribute("title", "Users List");

        Pageable pageRequest = PageRequest.of(page, 8);
        Page<User> users = userService.findAll(pageRequest);
        PageRender<User> pageRender = new PageRender<>("/list", users);

        model.addAttribute("users", users);

        model.addAttribute("page", pageRender);


        return "user/usersList";
    }

    @GetMapping(value = "/userDetails/{id}")
    public String viewUserDetails(@PathVariable(value = "id") Long id,
                                  Model model) {

        User user = userService.findOne(id);

        model.addAttribute("user", user);

        return "user/profile";

    }

    @GetMapping(value = "/create")
    public String create(Model model) {

        model.addAttribute("title", "Sign Up!");

        User user = new User();
        model.addAttribute("user", user);

        ClientsAddress clientsAddress = new ClientsAddress();
        clientsAddress.setUser(user);
        model.addAttribute("clientsAddress", clientsAddress);

        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);

        return "user/signup";
    }

    @GetMapping(value = "/view/{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       Map<String, Object> model,
                       RedirectAttributes flash) {

        User user;
        List<ClientsAddress> clientsAddresses;

        if (id > 0) {
            user = userService.findOne(id);
            if (user == null) {
                flash.addFlashAttribute("error", "The user does not exist");
                return "redirect:/usersList";
            }

            clientsAddresses = user.getClientsAddresses();

        } else {
            flash.addFlashAttribute("error", "The user does not exist");
            return "redirect:/usersList";
        }

        model.put("title", "Profile");
        model.put("user", user);
        model.put("clientAddress", clientsAddresses);

        return "user/signup";
    }


    @PostMapping(value = "/save")
    public String save(@ModelAttribute User user,
                       @ModelAttribute ClientsAddress clientsAddress,
                       RedirectAttributes flash) {


        String flashmessage = "Congratulation! You have an account";

        userService.save(user);


        clientAddressService.save(clientsAddress);


        flash.addFlashAttribute("success", flashmessage);
        return "redirect:list";
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
