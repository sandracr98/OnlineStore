package com.sandrajavaschool.OnlineStore.dao;

import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleDao extends JpaRepository<Role,Long> {

    //Metodo para buscar un rol por su nombre en la bbdd

    Optional<Role> findByName(String name);

}
