package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
/**
 * Service interface defining operations related to orders.
 */
public interface IOrderService {

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    List<Order> findAll();

    /**
     * Saves an order.
     *
     * @param order the order to be saved
     */
    void save(Order order);

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return the order with the specified ID, or null if not found
     */
    Order findOne(Long id);

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to be deleted
     */
    void delete(Long id);

    /**
     * Retrieves a list of orders within the specified date range.
     *
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @return a list of orders within the specified date range
     */
    List<Order> findByDateBetween(LocalDate startDate, LocalDate endDate);



}
