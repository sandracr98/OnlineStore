package com.sandrajavaschool.OnlineStore.service;

import com.sandrajavaschool.OnlineStore.dao.IClientAddressDao;
import com.sandrajavaschool.OnlineStore.dao.IOrderDao;
import com.sandrajavaschool.OnlineStore.dao.IProductDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IProductDao productDao;
    private final IOrderDao orderDao;
    private final IClientAddressDao clientAddressDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void save(User user) {
            userDao.save(user);
    }

    @Override
    public User findOne(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }



    @Override
    public List<Product> findByName(String term) {
        return productDao.findByName(term);
    }

    @Override
    public void saveOrder(Order order) {
        orderDao.save(order);
    }

    @Override
    public Product findProductById(Long id) {

        //maneja un optional de producto asi podemos saber si el
        //producto viene en la consulta

        return productDao.findById(id).orElse(null);

    }

    @Override
    public Order findOrderById(Long id) {
        return orderDao.findById(id).orElse(null);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDao.deleteById(id);
    }

    @Override
    public void saveClientAddress(ClientsAddress clientsAddress) {
        clientAddressDao.save(clientsAddress);
    }
    @Override
    public void deleteClientAddress(Long id) {
        clientAddressDao.deleteById(id);
    }

    @Override
    public Order fetchByIdWithUserReceiptLineProduct(Long id) {
        return orderDao.fetchByIdWithUserReceiptLineProduct(id);
    }

    @Override
    public User fetchByIdWithOrder(Long id) {
        return userDao.fetchByIdWithOrder(id);
    }

    public void saveInternalPhoto(MultipartFile photo, User user) {

        if (!photo.isEmpty()) {
            Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
            String rootPath = directorioRecursos.toFile().getAbsolutePath();
            try {
                byte[] bytes = photo.getBytes();
                Path rutaCompleta = Paths.get(rootPath + "//" + photo.getOriginalFilename());
                Files.write(rutaCompleta, bytes);

                user.setPhoto(photo.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }

    @Override
    public void saveExternalPhoto(MultipartFile photo, User user) {
        if (photo != null && !photo.isEmpty()) {
            if (user.getId() != null
                    && user.getId() > 0
                    && user.getPhoto() != null
                    && user.getPhoto().length() > 0) {

                Path rootPath = Paths.get("C:/temp/uploads/", user.getPhoto());
                File file = rootPath.toFile();

                if (file.exists() && file.canRead()) {
                    file.delete();
                }
            }
            try {
                // Generar un nombre único para el archivo
                String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();

                // Obtener la ruta completa del archivo
                Path completeRoot = Paths.get("C:/temp/uploads/", fileName);

                // Guardar la imagen en el sistema de archivos
                Files.write(completeRoot, photo.getBytes());

                // Establecer la ruta del archivo en el campo 'photo' del objeto 'User'
                user.setPhoto(fileName);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
