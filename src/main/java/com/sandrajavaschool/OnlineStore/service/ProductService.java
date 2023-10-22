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
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductDao productDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public Product findOne(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        productDao.deleteById(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productDao.findAll(pageable);
    }


    public Page<Product> findByName(String term, Pageable pageable) {
        return productDao.findByNameContaining(term, pageable);
    }


}
