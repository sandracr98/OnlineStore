package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IProductDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import com.sandrajavaschool.OnlineStore.service.implService.IStatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class StatisticService implements IStatisticService {

    private final IProductDao productDao;
    private final IUserDao userDao;
    private final IOrderService orderService;

    @Override
    public List<Product> getTop10Products() {
        return productDao.findTop10ByOrderByTotalSalesDesc();
    }

    @Override
    public List<User> getTop10Clients() {

        // Retrieve all users from the userDao
        List<User> top10Clients = userDao.findAll();

        // Calculate and set the total amount spent by each user
        return top10Clients.stream()
                .peek(user -> {
                    double total = user.getOrders().stream()
                            .mapToDouble(Order::getSum)
                            .sum();
                    user.setTotalSpent(total);
                })

                // Sort the users based on the total amount spent in descending order
                .sorted(Comparator.comparingDouble(User::getTotalSpent).reversed())

                // Limit the result to the top 10 clients
                .limit(10)

                // Collect the result into a list
                .collect(Collectors.toList());

    }

    @Override
    public double calculateMonthlyRevenue() {

        // Get the start and end dates of the current month
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        // Retrieve orders within the current month from the orderService
        List<Order> monthlyOrders = orderService.findByDateBetween(startOfMonth, endOfMonth);

        // Calculate the total revenue from the monthly orders
        double totalRevenue = monthlyOrders.stream().mapToDouble(Order::getTotal).sum();

        // Round the total revenue to two decimal places
        BigDecimal roundedRevenue = BigDecimal.valueOf(totalRevenue).setScale(2, RoundingMode.HALF_UP);

        // Convert the rounded revenue to a double value and return it
        return roundedRevenue.doubleValue();
    }

    public Map<String, Double> calculateLast5MonthsRevenue() {

        // Create a LinkedHashMap to store the revenue for the last 5 months
        Map<String, Double> last5MonthsRevenue = new LinkedHashMap<>();

        // Get the first day of the current month
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

        // Iterate over the last 5 months
        for (int i = 0; i < 5; i++) {
            // Get the last day of the current month
            LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());

            // Retrieve orders for the current month from the orderService
            List<Order> monthlyOrders = orderService.findByDateBetween(startOfMonth, endOfMonth);

            // Calculate the monthly revenue and add it to the map
            double monthlyRevenue = monthlyOrders.stream().mapToDouble(Order::getTotal).sum();
            last5MonthsRevenue.put(startOfMonth.getMonth().toString(), monthlyRevenue);

            // Move to the previous month
            startOfMonth = startOfMonth.minusMonths(1);
        }

        return last5MonthsRevenue;
    }


    @Override
    public double calculateWeeklyRevenue() {

        // Get the start and end dates of the current week (Monday to Sunday)
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // Retrieve orders within the current week from the orderService
        List<Order> weeklyOrders = orderService.findByDateBetween(startOfWeek, endOfWeek);

        // Calculate the total revenue from the weekly orders
        double totalRevenue = weeklyOrders.stream().mapToDouble(Order::getTotal).sum();

        // Round the total revenue to two decimal places
        BigDecimal roundedRevenue = BigDecimal.valueOf(totalRevenue).setScale(2, RoundingMode.HALF_UP);

        // Convert the rounded revenue to a double value and return it
        return roundedRevenue.doubleValue();

    }

}
