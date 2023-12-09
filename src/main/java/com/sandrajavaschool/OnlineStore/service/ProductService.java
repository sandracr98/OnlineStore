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


    @Override
    public void saveInternalPhoto(MultipartFile photo, Product product) {
        if (!photo.isEmpty()) {
            Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
            String rootPath = directorioRecursos.toFile().getAbsolutePath();
            try {
                byte[] bytes = photo.getBytes();
                Path rutaCompleta = Paths.get(rootPath + "//" + photo.getOriginalFilename());
                Files.write(rutaCompleta, bytes);

                product.setPhoto(photo.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void saveExternalPhoto(MultipartFile photo, Product product) {
        if (photo != null && !photo.isEmpty()) {
            if (product.getId() != null
                    && product.getId() > 0
                    && product.getPhoto() != null
                    && product.getPhoto().length() > 0) {

                Path rootPath = Paths.get("C:/temp/uploads/", product.getPhoto());
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
                product.setPhoto(fileName);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
