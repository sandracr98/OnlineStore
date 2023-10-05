package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IRoleDao;
import com.sandrajavaschool.OnlineStore.entities.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    @Transactional
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleDao.save(role);

    }

    @Override
    @Transactional
    public Role findOne(Long id) {
        return roleDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        roleDao.deleteById(id);
    }
}
