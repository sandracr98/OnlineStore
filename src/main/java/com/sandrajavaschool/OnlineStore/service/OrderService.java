package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IOrderDao;
import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderDao orderDao;

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public Order findOne(Long id) {
        return orderDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        orderDao.deleteById(id);
    }

    @Override
    public List<Order> findByDateBetween(LocalDate startDate, LocalDate endDate) {

        Date startOfDay = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant());

        return orderDao.findByDateBetween(startOfDay, endOfDay);
    }


}
