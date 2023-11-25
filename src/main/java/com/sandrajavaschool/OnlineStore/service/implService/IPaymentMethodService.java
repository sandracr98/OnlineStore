package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.PaymentMethod;

import java.util.List;

public interface IPaymentMethodService {

    public List<PaymentMethod> findAll();
    public void save(PaymentMethod paymentMethod);

    public PaymentMethod findOne(Long id);

    public void delete(Long id);
}
