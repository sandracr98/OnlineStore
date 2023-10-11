package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IOrderDao;
import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Override
    @Transactional
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    @Transactional
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    @Transactional
    public Order findOne(Long id) {
        return orderDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderDao.deleteById(id);
    }
}
