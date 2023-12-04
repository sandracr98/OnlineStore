package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface defining data access operations for the Role entity.
 */
@Repository
public interface IRoleDao extends JpaRepository<Role,Long> {

    /**
     * Finds a role by its name in the database.
     *
     * @param name The name of the role.
     * @return The role corresponding to the provided name.
     */
    Role findByName(String name);

}
