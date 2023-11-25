package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;

import java.util.List;

public interface IStadisticService {

    List<Product> getTop10Products();
    List<User> getTop10Clients();
    double calculateMonthlyRevenue();
    double calculateWeeklyRevenue();
}
