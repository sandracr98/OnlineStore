package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * Service interface defining operations related to products.
 */
public interface IProductService {

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    List<Product> findAll();

    /**
     * Saves a product.
     *
     * @param product the product to be saved
     */
    void save(Product product);

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return the product with the specified ID, or null if not found
     */
    Product findOne(Long id);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     */
    void delete(Long id);

    /**
     * Retrieves a page of products.
     *
     * @param pageable the pageable information
     * @return a page of products
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * Searches for products by name and returns a page of results.
     *
     * @param term     the search term for product names
     * @param pageable the pageable information
     * @return a page of products matching the search term
     */
    Page<Product> findByName(String term, Pageable pageable);

    /**
     * Saves an internal photo for a product.
     *
     * @param photo   the internal photo file
     * @param product the product to which the photo belongs
     */
    void saveInternalPhoto(MultipartFile photo, Product product);

    /**
     * Saves an external photo for a product.
     *
     * @param photo   the external photo file
     * @param product the product to which the photo belongs
     */
    void saveExternalPhoto(MultipartFile photo, Product product);
}
