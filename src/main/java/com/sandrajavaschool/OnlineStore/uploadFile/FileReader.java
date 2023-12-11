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

        // List to store parsed products
        List<Product> goodsList = new ArrayList<>();

        // ObjectMapper for JSON processing
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Attempt to read JSON content from the file input stream and convert it to a list of products
            goodsList = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            // Print the error and display an error message if JSON reading fails
            e.printStackTrace();
            System.err.println("Error reading JSON: " + e.getMessage());
        }

        return goodsList;
    }
}
