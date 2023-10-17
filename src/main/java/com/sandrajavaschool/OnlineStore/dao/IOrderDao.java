package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IOrderDao extends JpaRepository<Order,Long> {

    //vamos a crear un metodo para evitar que JPA haga tantas consultas a la bbdd
    //con el LAZZY, para ello creamos un metodo con FETCH personalizado a lo que pedimos
    //con los join conectamos todas las tablas y es mas rapido buscar to do de una vez

    @Query("select f from Order f join fetch f.user u join fetch f.receiptLines l join fetch l.product where f.id=?1")
    public Order fetchByIdWithUserReceiptLineProduct(Long id);
}
