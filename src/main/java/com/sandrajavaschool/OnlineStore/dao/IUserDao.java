package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserDao extends JpaRepository<User,Long> {

    @Query("select u from User u left join fetch u.orders o where o.id=?1")
    public User fetchByIdWithOrder(Long id);


    //Metodo para buscar un usuario por el email.
    //Viene integrado en el nombre del metodo, una consulta JPQL
    //para buscar por email.
    //Pero tambien se puede hacer usando el @Query como hemos hecho arriba

    public User findUserByEmail(String email);

    //Metodo para verificar si un usuario existe en nuestra bbdd
    Boolean existsByEmail(String email);
}
