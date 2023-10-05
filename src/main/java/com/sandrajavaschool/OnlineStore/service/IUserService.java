package com.sandrajavaschool.OnlineStore.service;


import com.sandrajavaschool.OnlineStore.entities.User;

import java.util.List;

public interface IUserService {

    public List<User> findAll();
    public void save(User user);

    public User findOne(Long id);

    public void delete(Long id);


}
