package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.ICategoryDao;
import com.sandrajavaschool.OnlineStore.entities.Category;
import com.sandrajavaschool.OnlineStore.service.implService.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryDao categoryDao;
    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public void save(Category category) {
        categoryDao.save(category);
    }

    @Override
    public Category findOne(Long id) {
        return categoryDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        categoryDao.deleteById(id);

    }
}
