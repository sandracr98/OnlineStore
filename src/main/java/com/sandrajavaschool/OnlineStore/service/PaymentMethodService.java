package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IPaymentMethodDao;
import com.sandrajavaschool.OnlineStore.entities.PaymentMethod;
import com.sandrajavaschool.OnlineStore.service.implService.IPaymentMethodService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService implements IPaymentMethodService {

    @Autowired
    private IPaymentMethodDao paymentMethodDao;

    @Override
    @Transactional
    public List<PaymentMethod> findAll() {
        return paymentMethodDao.findAll();
    }

    @Override
    @Transactional
    public void save(PaymentMethod paymentMethod) {
        paymentMethodDao.save(paymentMethod);
    }

    @Override
    @Transactional
    public PaymentMethod findOne(Long id) {
        return paymentMethodDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        paymentMethodDao.deleteById(id);
    }
}
