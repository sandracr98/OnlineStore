package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IProductDao;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.service.implService.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Override
    @Transactional
    public Page<Product> findAll(Pageable pageable) {
        return productDao.findAll(pageable);
    }

}
