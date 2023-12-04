package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface defining data access operations for the User entity.
 */
@Repository
public interface IUserDao extends JpaRepository<User,Long> {

    /**
     * Retrieves a user by their identifier with associated orders.
     *
     * @param id The user identifier.
     * @return The user with associated orders, or null if not found.
     */
    @Query("select u from User u left join fetch u.orders o where o.id=?1")
    public User fetchByIdWithOrder(Long id);


    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user.
     * @return The user corresponding to the provided email address.
     */
    User findByEmail(String email);

    /**
     * Checks if a user exists in the database with the given email address.
     *
     * @param email The email address to check.
     * @return True if a user with the given email address exists, false otherwise.
     */
    Boolean existsByEmail(String email);
}
