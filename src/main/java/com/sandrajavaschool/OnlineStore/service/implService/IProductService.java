package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    public List<Product> findAll();

    public void save(Product product);

    public Product findOne(Long id);

    public void delete(Long id);

    public Page<Product> findAll(Pageable pageable);

}
