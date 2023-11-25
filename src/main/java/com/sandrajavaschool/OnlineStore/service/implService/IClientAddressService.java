package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.User;

import java.util.List;

public interface IClientAddressService {

    public List<ClientsAddress> findAll();
    public void save(ClientsAddress clientsAddress);

    public ClientsAddress findOne(Long id);

    public void delete(Long id);
}
