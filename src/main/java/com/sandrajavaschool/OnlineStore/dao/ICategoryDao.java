package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface defining data access operations for the Category entity.
 */
public interface ICategoryDao extends JpaRepository<Category, Long> {

}
