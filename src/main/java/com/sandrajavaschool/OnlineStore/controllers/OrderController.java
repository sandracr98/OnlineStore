package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {

    final private IUserService userService;

    @Autowired
    public OrderController(IUserService userService) {
        this.userService = userService;
    }


    @GetMapping("receipt/{userId}")
    public String create(@PathVariable(value = "userId") Long userId,
                         Model model) {

        User user = userService.findOne(userId);

        Order order = new Order();
        order.setUser(user); //de esta forma asignamos una factura con un cliente

        model.addAttribute("order", order);
        model.addAttribute("title", "Shopping cart");

        return "order/receipt";
    }




}
