package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductDao extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.title like %?1%")
    List<Product> findByName(String term);

}
