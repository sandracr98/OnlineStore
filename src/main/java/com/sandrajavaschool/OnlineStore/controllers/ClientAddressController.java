package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IClientAddressService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequiredArgsConstructor
@RequestMapping("/address")

public class ClientAddressController {

    final private IUserService userService;
    private final IClientAddressService clientAddressService;

    @GetMapping(value = "/create/{userId}")
    public String create(@PathVariable(value = "userId") Long userId,
                         Model model) {

        User user = userService.findOne(userId);

        ClientsAddress clientsAddress = new ClientsAddress();
        user.addAddress(clientsAddress);


        model.addAttribute("title", "New Address!");
        model.addAttribute("clientsAddress", clientsAddress);

        return "address/addressCreate";
    }

    @PostMapping(value = "/saveAddress")
    public String save(@ModelAttribute("clientsAddress") ClientsAddress clientsAddress,
                       RedirectAttributes flash) {


        String flashmessage = "Congratulation! You create a new address";

        clientAddressService.save(clientsAddress);

        flash.addFlashAttribute("success", flashmessage);

        return "redirect:/userDetails/" + clientsAddress.getUser().getId();
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       Model model,
                       RedirectAttributes flash) {

        ClientsAddress clientsAddress = null;

        if (id > 0) {

            clientsAddress = clientAddressService.findOne(id);

            if (clientsAddress == null) {
                flash.addFlashAttribute("error", "The address does not exist");
                //IMPLEMENTAR MANEJO DE ERRORES
                return "redirect:/userDetails/" + clientsAddress.getUser().getId();
            }

        } else {
            flash.addFlashAttribute("error", "The address does not exist");
            return "redirect:/userDetails/" + clientsAddress.getUser().getId();
        }

        model.addAttribute("title", "Edit Address");
        model.addAttribute("clientsAddress", clientsAddress);

        return "address/addressCreate";

    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes flash) {

        ClientsAddress clientsAddress = clientAddressService.findOne(id);
        User user = clientsAddress.getUser();

        if (id > 0) {
            clientAddressService.delete(id);
            flash.addFlashAttribute("success", "The address has been deleted");
        }


        return "redirect:/userDetails/" + user.getId();
    }
}
