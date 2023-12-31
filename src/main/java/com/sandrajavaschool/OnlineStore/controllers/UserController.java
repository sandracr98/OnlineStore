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
                       Map<String, Object> model) {

        // 1. Initialize a User object.
        User user = null;

        // 2. Check if the user with the specified ID exists.
        if (id <= 0 || (userService.findOne(id)) == null) {
            throw new UserNotFoundException("The user does not exist");
        }

        // 3. retrieves the User object using the userService with the provided ID
        user = userService.findOne(id);

        // Set the title and user attributes in the model.
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

        // 1. Check for validation errors in the User object

        if (result.hasErrors()) {
            model.addAttribute("title", "Sign Up!");
            return "redirect:/create";
        }

        // 2. If the user is new and a user with the same email already exists,
        // redirect with an error message.

        if (user.getId() == null && userDao.existsByEmail(user.getEmail())) {
            String message = "The user already exist";
            flash.addFlashAttribute("error", message);
            return "user/signup";
        }

        // 3. If a photo is provided, save it using the userService.
        if (photo != null && !photo.isEmpty()) {
            userService.saveExternalPhoto(photo, user);
        }

        // 4. If the user is new, encode the password before saving it to the database.
        if (user.getId() == null) {
            user.setPass(passwordEncoder.encode(user.getPass()));
        }

        // 5. Assign the "ROLE_USER" role to the user.
        Role role = roleDao.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));

        userService.save(user);

        // 7. Add a success message and redirect to the user details page.
        flash.addFlashAttribute("success", "Congratulation! Your user has been saved");

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

    @GetMapping("/change-password/{id}")
    public String showChangePasswordForm(@PathVariable(value = "id") Long id,
                                         Model model) {

        User currentUser = userService.findOne(id);
        model.addAttribute("currentUser", currentUser);

        return "user/changePasswordView";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @ModelAttribute("currentUser") User currentUser,
                                 Model model,
                                 RedirectAttributes flash) {

        Long userId = currentUser.getId();
        User user = userService.findOne(userId);

        if (passwordEncoder.matches(oldPassword, user.getPass())) {

            user.setPass(passwordEncoder.encode(newPassword));
            userService.save(user);

            model.addAttribute("success", "Password has been changed");

        } else {

            model.addAttribute("error", "Password does not match");
        }

        return "redirect:/userDetails/" + currentUser.getId();
    }


}
