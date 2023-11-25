package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.PaymentMethod;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentMethodDao extends JpaRepository<PaymentMethod,Long> {

}
