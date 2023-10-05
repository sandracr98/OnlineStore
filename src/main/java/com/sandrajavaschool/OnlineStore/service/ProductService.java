package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IProductDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    @Transactional
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    @Transactional
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    @Transactional
    public Product findOne(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDao.deleteById(id);
    }
}