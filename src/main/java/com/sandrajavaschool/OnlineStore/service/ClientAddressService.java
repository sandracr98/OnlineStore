package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IClientAddressDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IClientAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientAddressService implements IClientAddressService {


    private final IClientAddressDao clientAddressDao;

    @Override
    public List<ClientsAddress> findAll() {
        return clientAddressDao.findAll();
    }

    @Override
    public void save(ClientsAddress clientsAddress) {
        clientAddressDao.save(clientsAddress);
    }

    @Override
    public ClientsAddress findOne(Long id) {
        return clientAddressDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        clientAddressDao.deleteById(id);
    }

}
