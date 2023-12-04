package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Role;

import java.util.List;

/**
 * Service interface defining operations related to Role and associated entities.
 */
public interface IRoleService {

    /**
     * Retrieves all roles.
     *
     * @return a list of all roles
     */
    List<Role> findAll();

    /**
     * Saves a role.
     *
     * @param role the role to be saved
     */
    void save(Role role);

    /**
     * Retrieves a role by its ID.
     *
     * @param id the ID of the role
     * @return the role with the specified ID, or null if not found
     */
    Role findOne(Long id);

    /**
     * Deletes a role by its ID.
     *
     * @param id the ID of the role to be deleted
     */
    void delete(Long id);

}
