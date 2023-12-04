package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.PaymentMethod;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface defining data access operations for the PaymentMethod entity.
 */
public interface IPaymentMethodDao extends JpaRepository<PaymentMethod,Long> {

}
