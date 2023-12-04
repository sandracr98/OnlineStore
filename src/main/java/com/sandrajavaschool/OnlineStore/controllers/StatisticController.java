package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
public class StatisticController {


    private final IStatisticService stadisticService;

    @GetMapping(value = "/top10Products")
    public String list(Model model) {

        model.addAttribute("title", "Sales Statistics");

        List<Product> products = stadisticService.getTop10Products();
        List<User> users = stadisticService.getTop10Clients();
        double monthlyRevenue = stadisticService.calculateMonthlyRevenue();
        double weeklyRevenue = stadisticService.calculateWeeklyRevenue();

        Map<String, Double> monthlyRevenueData = stadisticService.calculateLast5MonthsRevenue();


        model.addAttribute("products", products);
        model.addAttribute("users", users);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("weeklyRevenue", weeklyRevenue);
        model.addAttribute("monthlyRevenueData", monthlyRevenueData);

        return "sales/sales";
    }


}
