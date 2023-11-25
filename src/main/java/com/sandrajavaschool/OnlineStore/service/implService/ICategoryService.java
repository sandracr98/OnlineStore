package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Category;

import java.util.List;

public interface ICategoryService {

    public List<Category> findAll();
    public void save(Category category);

    public Category findOne(Long id);

    public void delete(Long id);

}
