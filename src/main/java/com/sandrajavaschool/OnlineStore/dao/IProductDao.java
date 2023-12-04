package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface defining data access operations for the Product entity.
 */
public interface IProductDao extends JpaRepository<Product, Long> {

    /**
     * Finds products by name containing the specified term.
     *
     * @param term The search term.
     * @return A list of products with names containing the specified term.
     */
    @Query("select p from Product p where p.title like %?1%")
    List<Product> findByName(String term);

    //falta  por precio +++

    /**
     * Finds products by various attributes containing the specified term, with pagination.
     *
     * @param term     The search term.
     * @param pageable The pagination information.
     * @return A page of products matching the specified term.
     */
    @Query("select p from Product p where p.title like %?1%"
            + "or p.brand like %?1%"
            + "or p.category.name like %?1%"
            + "or p.weight like %?1%"
            + "or p.volume like %?1%"
            + "or p.color like %?1%")
    Page<Product> findByNameContaining(String term, Pageable pageable);


    /**
     * Finds the top 10 products ordered by total sales in descending order.
     *
     * @return The top 10 products by total sales.
     */
    List<Product> findTop10ByOrderByTotalSalesDesc();


}
