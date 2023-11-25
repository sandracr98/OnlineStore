package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductDao extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.title like %?1%")
    List<Product> findByName(String term);

    //falta por date y por precio +++
    @Query("select p from Product p where p.title like %?1%"
            + "or p.brand like %?1%"
            + "or p.category.name like %?1%"
            + "or p.weight like %?1%"
            + "or p.volume like %?1%"
            + "or p.color like %?1%")
    Page<Product> findByNameContaining(String term, Pageable pageable);

    List<Product> findTop10ByOrderByTotalSalesDesc();


}
