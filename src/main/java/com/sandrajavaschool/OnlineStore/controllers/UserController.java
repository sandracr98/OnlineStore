package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.dao.IRoleDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.errorsException.UserNotFoundException;
import com.sandrajavaschool.OnlineStore.paginator.PageRender;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import lombok.RequiredArgsConstructor;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;

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
    private final PasswordEncoder passwordEncoder;
    private final IRoleDao roleDao;
    private final IUserDao userDao;


    //@Autowired
    //public UserController(IUserService userService, IRoleService roleService, IClientAddressService clientAddressService) {
    //  this.userService = userService;
    //this.roleService = roleService;
    //this.clientAddressService = clientAddressService;
    //}

    @GetMapping(value = {"/mainPage", "/"})
    public String mainPage(Model model) {
        model.addAttribute("title", "SCR");
        return "/mainPage";
    }

    @GetMapping(value = "/list")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page,
                       Model model,
                       Authentication authentication) {

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
                                  RedirectAttributes flash,
                                  Model model) {

        User user = userService.findOne(id);

        //con esto en una sola consulta jpa trae al cliente con todas sus facturas
        //User user = userService.fetchByIdWithOrder(id);

        if (user == null) {
            throw new UserNotFoundException("The user is not found");
        }


        model.addAttribute("user", user);
        model.addAttribute("title", "Profile: " + user.getName());

        return "user/profile";

    }

    @GetMapping(value = "/create")
    @PreAuthorize("isAnonymous() or hasRole('ROLE_ADMIN')")
    public String create(Model model) {

        model.addAttribute("title", "Sign Up!");

        User user = new User();

        model.addAttribute("user", user);

        return "user/signup";
    }

    @GetMapping(value = "/view/{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       Map<String, Object> model,
                       RedirectAttributes flash) {

        User user = null;

        if (id > 0) {
            user = userService.findOne(id);
            if (user == null) {
                throw new UserNotFoundException("The user does not exist");
            }

        } else {
            throw new UserNotFoundException("The user does not exist");
        }

        model.put("title", "Profile");
        model.put("user", user);

        return "user/editProfile";
    }


    @PostMapping(value = "/save")
    public String save(@ModelAttribute("user") User user,
                       BindingResult result,
                       Model model,
                       @RequestParam("file") MultipartFile photo,
                       RedirectAttributes flash) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Sign Up!");
            return "redirect:/create";
        }

        if (user.getId() == null && userDao.existsByEmail(user.getEmail())) {
            String message = "The user already exist";
            flash.addFlashAttribute("error", message);
            return "user/signup";
        }
        if (photo != null && !photo.isEmpty()) {
            userService.saveExternalPhoto(photo, user);
        }

        if (user.getId() == null) {
            user.setPass(passwordEncoder.encode(user.getPass()));
        }

        Role role = roleDao.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));

        userService.save(user);

        flash.addFlashAttribute("success", "Congratulation! You have an account");

        return "redirect:/userDetails/" + user.getId();

    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes flash) {

        if (id <= 0) {
            flash.addFlashAttribute("error", "The user has not been deleted");
            return "redirect:/list";
        }

        userService.delete(id);
        flash.addFlashAttribute("success", "The user has been deleted");

        return "redirect:/list";
    }

    @GetMapping(value = "/getId")
    public String getId(Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        Long userId = user.getId();

        return "redirect:/userDetails/" + userId;
    }


}
