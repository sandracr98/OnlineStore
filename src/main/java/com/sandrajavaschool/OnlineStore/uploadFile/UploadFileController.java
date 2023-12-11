package com.sandrajavaschool.OnlineStore.uploadFile;

import com.sandrajavaschool.OnlineStore.dao.IProductDao;
import com.sandrajavaschool.OnlineStore.dao.IRoleDao;
import com.sandrajavaschool.OnlineStore.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UploadFileController {

    private final IProductDao productDao;

    @GetMapping(value = "/uploadFileForm")
    public String view() {
        return "product/uploadFileForm";
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam MultipartFile file,
                         ModelMap modelMap) {

        // Check if the uploaded file is empty
        if (file.isEmpty()) {
            modelMap.addAttribute("message", "Por favor, seleccione un archivo.");
            return "fileUploadView";
        }

        try {
            // Parse the goods file using FileReader utility method
            List<Product> goodsList = FileReader.parseGoodsFile(file);
            // Save the parsed products to the database using productDao
            productDao.saveAll(goodsList);

            modelMap.addAttribute("message", "File upload successful.");

        } catch (IOException e) {
            e.printStackTrace();
            modelMap.addAttribute("message", "Error processing the file.");
        }

        return "product/fileUploadView";
    }



}
