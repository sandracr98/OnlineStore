package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.User;

import java.util.List;

/**
 * Service interface defining operations related to client addresses.
 */
public interface IClientAddressService {

    /**
     * Retrieves all client addresses.
     *
     * @return a list of all client addresses
     */
    List<ClientsAddress> findAll();

    /**
     * Saves a client address.
     *
     * @param clientsAddress the client address to be saved
     */
    void save(ClientsAddress clientsAddress);

    /**
     * Retrieves a client address by its ID.
     *
     * @param id the ID of the client address
     * @return the client address with the specified ID, or null if not found
     */
    ClientsAddress findOne(Long id);

    /**
     * Deletes a client address by its ID.
     *
     * @param id the ID of the client address to be deleted
     */
    void delete(Long id);
}


