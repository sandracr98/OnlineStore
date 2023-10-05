package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDao extends JpaRepository<Order,Long> {

}
