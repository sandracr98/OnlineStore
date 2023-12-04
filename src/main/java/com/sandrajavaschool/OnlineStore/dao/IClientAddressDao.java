package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface defining data access operations for the Address entity.
 */
public interface IClientAddressDao extends JpaRepository<ClientsAddress,Long> {

}
