package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IOrderService {

    public List<Order> findAll();
    public void save(Order order);

    public Order findOne(Long id);

    public void delete(Long id);
    List<Order> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
