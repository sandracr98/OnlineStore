package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.entities.Product;

import java.util.List;

public interface IProductService {

    public List<Product> findAll();

    public void save(Product product);

    public Product findOne(Long id);

    public void delete(Long id);

}
