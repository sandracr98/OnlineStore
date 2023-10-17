package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserDao extends JpaRepository<User,Long> {

    @Query("select u from User u left join fetch u.orders o where o.id=?1")
    public User fetchByIdWithOrder(Long id);
}
