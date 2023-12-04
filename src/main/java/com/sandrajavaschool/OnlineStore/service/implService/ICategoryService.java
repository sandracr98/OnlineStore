package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Category;

import java.util.List;

/**
 * Service interface defining operations related to product categories.
 */
public interface ICategoryService {

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    List<Category> findAll();

    /**
     * Saves a category.
     *
     * @param category the category to be saved
     */
    void save(Category category);

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category
     * @return the category with the specified ID, or null if not found
     */
    Category findOne(Long id);

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to be deleted
     */
    void delete(Long id);
}

