package com.sandrajavaschool.OnlineStore.service.implService;


import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IUserService {

    public List<User> findAll();
    public void save(User user);

    public User findOne(Long id);

    public void delete(Long id);

    public Page<User> findAll(Pageable pageable);

    public List<Product> findByName(String term);

    public void saveOrder(Order order);

    public Product findProductById(Long id);

    public Order findOrderById(Long id);

    public void deleteOrder(Long id);

    public Order fetchByIdWithUserReceiptLineProduct(Long Id);

    public User fetchByIdWithOrder(Long id);


}
