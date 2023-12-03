package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Category;
import com.sandrajavaschool.OnlineStore.entities.CategoryStatus;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.paginator.PageRender;
import com.sandrajavaschool.OnlineStore.service.implService.ICategoryService;
import com.sandrajavaschool.OnlineStore.service.implService.IProductService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
@SessionAttributes("Product")
@RequiredArgsConstructor
public class ProductController {

    final private IProductService productService;
    final private ICategoryService categoryService;

    @GetMapping(value = "/productsList")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page,
                       Model model,
                       @RequestParam(name = "term", required = false) String term,
                       HttpSession session) {

        model.addAttribute("title", "Products");

        session.setAttribute("term", term);

        Page<Product> products;
        Pageable pageRequest = PageRequest.of(page, 4);


        if (term != null && !term.isEmpty()) {
            model.addAttribute("additionalParams", "&term=" + term);
            products = productService.findByName(term, pageRequest);
        } else {
            products = productService.findAll(pageRequest);
        }

        PageRender<Product> pageRender = new PageRender<>("/productsList", products);

        model.addAttribute("products", products);
        model.addAttribute("term", term);
        model.addAttribute("page", pageRender);

        return "product/productsList";
    }

    @GetMapping(value = "productPhoto/{id}")
    public String view(@PathVariable(value = "id") Long id,
                       Map<String, Object> model,
                       RedirectAttributes flash) {

        Product product = productService.findOne(id);
        if (product == null) {
            flash.addFlashAttribute("error", "The product does not exist");
            return "redirect:/productsList";
        }
        model.put("tittle", "");
        model.put("product", product);

        return "product/productCreate";

    }

    @GetMapping(value = "/createProduct")
    public String create(Model model) {

        try {
            model.addAttribute("title", "New Product");

            Product product = new Product();
            model.addAttribute("product", product);

            List<Category> categories = categoryService.findAll();

            if (categories == null) {
                throw new IllegalStateException("CategoryList is null");
            }

            model.addAttribute("categories", categories);

            return "product/productCreate";

        } catch (Exception e) {
            return "error/error405";
        }
    }

    @PostMapping(value = "/saveProduct")
    public String save(@ModelAttribute Product product,
                       RedirectAttributes flash,
                       @RequestParam("file") MultipartFile photo) {

        String flashmessage = "Your item has been created/edited correctly!";

        productService.saveInternalPhoto(photo, product);

        //para que se guarde la fecha cada vez que lo modificas
        product.prePersist();

        productService.save(product);

        flash.addFlashAttribute("success", flashmessage);

        return "redirect:productsList";
    }

    @GetMapping(value = "/editProduct/{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       Model model,
                       RedirectAttributes flash) {

        Product product = null;

        if (id > 0) {
            product = productService.findOne(id);
            if (product == null) {
                flash.addFlashAttribute("error", "The product does not exist");
                return "redirect:/product/productList";
            }

        } else {
            flash.addFlashAttribute("error", "The product does not exist");
            return "redirect:/product/productList";
        }


        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        model.addAttribute("product", product);

        return "product/productCreate";

    }

    @RequestMapping(value = "/deleteProduct/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes flash) {

        if (id <= 0) {
            return "redirect:/productsList";
        }

        Product product = productService.findOne(id);

        if (product == null || product.getReceiptLine() == null || product.getReceiptLine().isEmpty()) {
            productService.delete(id);
            flash.addFlashAttribute("success", "The product has been deleted");

        } else {
            product.setStatus(false);
            productService.save(product);
            flash.addFlashAttribute("error", "The product is associated with one or more products and cannot be deleted.");
        }


        return "redirect:/productsList";
    }


}
