package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IStadisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class StadisticController {


    private final IStadisticService stadisticService;

    @GetMapping(value = "/top10Products")
    public String list(Model model) {

        model.addAttribute("title", "Sales Stadistics");

        List<Product> products = stadisticService.getTop10Products();
        List<User> users = stadisticService.getTop10Clients();
        double monthlyRevenue = stadisticService.calculateMonthlyRevenue();
        double weeklyRevenue = stadisticService.calculateWeeklyRevenue();

        model.addAttribute("products", products);
        model.addAttribute("users", users);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("weeklyRevenue", weeklyRevenue);

        return "sales/sales";
    }


}
