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

        if (file.isEmpty()) {
            modelMap.addAttribute("message", "Por favor, seleccione un archivo.");
            return "fileUploadView";
        }

        try {
            List<Product> goodsList = FileReader.parseGoodsFile(file);
            productDao.saveAll(goodsList);
            modelMap.addAttribute("message", "Carga de archivo exitosa.");

        } catch (IOException e) {
            e.printStackTrace();
            modelMap.addAttribute("message", "Error al procesar el archivo.");
        }

        return "product/fileUploadView";
    }



}
