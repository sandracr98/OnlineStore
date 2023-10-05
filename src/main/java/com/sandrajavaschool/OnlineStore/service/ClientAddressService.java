package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IClientAddressDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientAddressService implements IClientAddressService {

    @Autowired
    private IClientAddressDao clientAddressDao;

    @Override
    @Transactional
    public List<ClientsAddress> findAll() {
        return clientAddressDao.findAll();
    }

    @Override
    @Transactional
    public void save(ClientsAddress clientsAddress) {
        clientAddressDao.save(clientsAddress);
    }

    @Override
    @Transactional
    public ClientsAddress findOne(Long id) {
        return clientAddressDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clientAddressDao.deleteById(id);
    }
}
