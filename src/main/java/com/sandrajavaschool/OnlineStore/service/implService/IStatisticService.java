package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;

import java.util.List;
import java.util.Map;

/**
 * Service interface defining operations related to statistical data.
 */
public interface IStatisticService {
    /**
     * Gets the top 10 products based on total sales.
     *
     * @return A list of the top 10 products.
     */
    List<Product> getTop10Products();

    /**
     * Gets the top 10 clients based on their activity.
     *
     * @return A list of the top 10 clients.
     */
    List<User> getTop10Clients();

    /**
     * Calculates the monthly revenue.
     *
     * @return The total revenue for the current month.
     */
    double calculateMonthlyRevenue();

    /**
     * Calculates the weekly revenue.
     *
     * @return The total revenue for the current week.
     */
    double calculateWeeklyRevenue();

    /**
     * Calculates the revenue for the last 5 months.
     *
     * @return A map where keys are month names and values are corresponding revenues.
     */
    Map<String, Double> calculateLast5MonthsRevenue();

    /**
     * Calculates the revenue for the last 7 days.
     *
     * @return A map where keys are day names and values are corresponding revenues.
     */

    Map<String, Double> calculateLast7DaysRevenue();

}
