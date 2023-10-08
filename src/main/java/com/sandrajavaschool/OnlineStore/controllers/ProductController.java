package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.ClientsAddress;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.paginator.PageRender;
import com.sandrajavaschool.OnlineStore.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("Product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping(value = "/productsList")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page,
                       Model model) {

        model.addAttribute("title", "Products");

        Pageable pageRequest = PageRequest.of(page, 8);
        Page<Product> products = productService.findAll(pageRequest);
        PageRender<Product> pageRender = new PageRender<>("/productsList", products);

        model.addAttribute("products", products);

        model.addAttribute("page", pageRender);


        return "product/productsList";
    }

    @GetMapping(value = "/createProduct")
    public String create(Model model) {

        model.addAttribute("title", "New Product");

        Product product = new Product();
        model.addAttribute("product", product);

        return "product/productCreate";
    }

    @PostMapping(value = "/saveProduct")
    public String save(@ModelAttribute Product product,
                       RedirectAttributes flash) {

        String flashmessage = "Your item has been created";

        productService.save(product);

        flash.addFlashAttribute("success", flashmessage);

        return "redirect:productsList";
    }



}
