package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.entities.Order;

import java.util.List;

public interface IOrderService {

    public List<Order> findAll();
    public void save(Order order);

    public Order findOne(Long id);

    public void delete(Long id);

}
