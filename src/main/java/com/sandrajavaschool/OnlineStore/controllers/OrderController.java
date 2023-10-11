package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {

    final private IUserService userService;
    final private IOrderService orderService;

    @Autowired
    public OrderController(IUserService userService, IOrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
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

    @GetMapping(value = "/load-products/{term}", produces = {"application/json"})
    public @ResponseBody List<Product> loadProducts(@PathVariable String term) {
        return userService.findByName(term);
    }


}
