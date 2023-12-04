package com.sandrajavaschool.OnlineStore.service.implService;


import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * Service interface defining operations related to User and associated entities.
 */
public interface IUserService {

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    List<User> findAll();

    /**
     * Saves a user.
     *
     * @param user The user to save.
     */
    void save(User user);

    /**
     * Retrieves a user by their identifier.
     *
     * @param id The user identifier.
     * @return The user with the specified identifier.
     */
    User findOne(Long id);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user.
     * @return The user with the specified email address.
     */
    User findByEmail(String email);

    /**
     * Deletes a user by their identifier.
     *
     * @param id The user identifier.
     */
    void delete(Long id);

    /**
     * Retrieves all users with pagination.
     *
     * @param pageable The pagination information.
     * @return A page of users.
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Finds products by name containing the specified term.
     *
     * @param term The search term.
     * @return A list of products with names containing the specified term.
     */
    List<Product> findByName(String term);

    /**
     * Saves an order.
     *
     * @param order The order to save.
     */
    void saveOrder(Order order);

    /**
     * Finds a product by its identifier.
     *
     * @param id The product identifier.
     * @return The product with the specified identifier.
     */
    Product findProductById(Long id);

    /**
     * Finds an order by its identifier.
     *
     * @param id The order identifier.
     * @return The order with the specified identifier.
     */
    Order findOrderById(Long id);

    /**
     * Deletes an order by its identifier.
     *
     * @param id The order identifier.
     */
    void deleteOrder(Long id);

    /**
     * Saves an internal photo for a user.
     *
     * @param photo The internal photo file.
     * @param user  The user associated with the photo.
     */
    void saveInternalPhoto(MultipartFile photo, User user);

    /**
     * Saves an external photo for a user.
     *
     * @param photo The external photo file.
     * @param user  The user associated with the photo.
     */
    void saveExternalPhoto(MultipartFile photo, User user);

}

