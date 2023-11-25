package com.sandrajavaschool.OnlineStore.service.implService;

import com.sandrajavaschool.OnlineStore.entities.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> findAll();
    public void save(Role role);

    public Role findOne(Long id);

    public void delete(Long id);

}
