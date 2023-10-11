package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IProductDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IProductDao productDao;


    @Override
    @Transactional
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public User findOne(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public Page<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    @Transactional
    public List<Product> findByName(String term) {
        return productDao.findByName(term);
    }
}
