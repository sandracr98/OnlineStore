package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IProductDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import com.sandrajavaschool.OnlineStore.service.implService.IStadisticService;
import jakarta.transaction.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class StadisticService implements IStadisticService {

    private final IProductDao productDao;
    private final IUserDao userDao;
    private final IOrderService orderService;
    @Override
    public List<Product> getTop10Products() {
        return productDao.findTop10ByOrderByTotalSalesDesc();
    }

    @Override
    public List<User> getTop10Clients() {

        List<User> top10Clients = userDao.findAll();

        return top10Clients.stream()
                .peek(user -> {
                    double total = user.getOrders().stream()
                            .mapToDouble(Order::getSum)
                            .sum();
                    user.setTotalSpent(total);
                })
                .sorted(Comparator.comparingDouble(User::getTotalSpent).reversed())
                .limit(10)
                .collect(Collectors.toList());

    }

    @Override
    public double calculateMonthlyRevenue() {
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        List<Order> monthlyOrders = orderService.findByDateBetween(startOfMonth, endOfMonth);

        return monthlyOrders.stream().mapToDouble(Order::getTotal).sum();
    }

    public Map<String, Double> calculateLast5MonthsRevenue() {
        Map<String, Double> last5MonthsRevenue = new LinkedHashMap<>();

        // Obtén el primer día del mes actual
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

        // Itera sobre los últimos 5 meses
        for (int i = 0; i < 5; i++) {
            // Obtén el último día del mes actual
            LocalDate endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth());

            // Obtén los pedidos para el mes actual
            List<Order> monthlyOrders = orderService.findByDateBetween(startOfMonth, endOfMonth);

            // Calcula el ingreso mensual y agrégalo al mapa
            double monthlyRevenue = monthlyOrders.stream().mapToDouble(Order::getTotal).sum();
            last5MonthsRevenue.put(startOfMonth.getMonth().toString(), monthlyRevenue);

            // Avanza al mes anterior
            startOfMonth = startOfMonth.minusMonths(1);
        }

        return last5MonthsRevenue;
    }


    @Override
    public double calculateWeeklyRevenue() {
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<Order> weeklyOrders = orderService.findByDateBetween(startOfWeek, endOfWeek);

        return weeklyOrders.stream().mapToDouble(Order::getTotal).sum();

    }
}
