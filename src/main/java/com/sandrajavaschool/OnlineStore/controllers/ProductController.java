package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.paginator.PageRender;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@SessionAttributes("Product")
@RequiredArgsConstructor
public class ProductController {

    final private IProductService productService;

    @GetMapping(value = "/productsList")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page,
                       Model model,
                       @RequestParam(name = "term", required = false) String term,
                       HttpSession session) {
        //diferencias entre @param y request param

        model.addAttribute("title", "Products");

        session.setAttribute("term", term);


        Page<Product> products;
        Pageable pageRequest = PageRequest.of(page, 5);


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


        model.addAttribute("product", product);

        return "product/productCreate";

    }

    @RequestMapping(value = "/deleteProduct/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes flash) {

        if (id > 0) {
            productService.delete(id);
            flash.addFlashAttribute("success", "The product has been deleted");
        }

        return "redirect:/list";
    }
}
