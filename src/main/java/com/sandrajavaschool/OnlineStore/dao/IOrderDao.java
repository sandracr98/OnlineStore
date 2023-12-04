package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Interface defining data access operations for the Order entity.
 */
public interface IOrderDao extends JpaRepository<Order,Long> {

    //vamos a crear un metodo para evitar que JPA haga tantas consultas a la bbdd
    //con el LAZZY, para ello creamos un metodo con FETCH personalizado a lo que pedimos
    //con los join conectamos todas las tablas y es mas rapido buscar to do de una vez

    /**
     * Retrieves an order by its identifier with associated user, receipt lines, and products.
     *
     * @param id The order identifier.
     * @return The order with associated user, receipt lines, and products.
     */
    @Query("select f from Order f join fetch f.user u join fetch f.receiptLines l join fetch l.product where f.id=?1")
    public Order fetchByIdWithUserReceiptLineProduct(Long id);

    /**
     * Finds orders within a specified date range.
     *
     * @param startDate The start date of the range.
     * @param endDate   The end date of the range.
     * @return A list of orders within the specified date range.
     */
    List<Order> findByDateBetween(Date startDate, Date endDate);

}
