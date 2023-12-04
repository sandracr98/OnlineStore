package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.PaymentMethod;

import java.util.List;

/**
 * Service interface defining operations related to payment methods.
 */
public interface IPaymentMethodService {

    /**
     * Retrieves all payment methods.
     *
     * @return a list of all payment methods
     */
    List<PaymentMethod> findAll();

    /**
     * Saves a payment method.
     *
     * @param paymentMethod the payment method to be saved
     */
    void save(PaymentMethod paymentMethod);

    /**
     * Retrieves a payment method by its ID.
     *
     * @param id the ID of the payment method
     * @return the payment method with the specified ID, or null if not found
     */
    PaymentMethod findOne(Long id);

    /**
     * Deletes a payment method by its ID.
     *
     * @param id the ID of the payment method to be deleted
     */
    void delete(Long id);
}
