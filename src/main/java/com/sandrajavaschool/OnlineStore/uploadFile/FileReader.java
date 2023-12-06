package com.sandrajavaschool.OnlineStore.uploadFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandrajavaschool.OnlineStore.entities.Product;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileReader {
    public static List<Product> parseGoodsFile(MultipartFile file) throws IOException {

        List<Product> goodsList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            goodsList = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al leer el JSON: " + e.getMessage());
        }


        return goodsList;
    }
}
