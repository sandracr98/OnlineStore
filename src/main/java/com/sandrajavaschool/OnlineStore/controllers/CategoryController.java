package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Category;
import com.sandrajavaschool.OnlineStore.entities.CategoryStatus;
import com.sandrajavaschool.OnlineStore.service.implService.ICategoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("Category")
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping(value = "/categoryList")
    public String list(Model model) {

        model.addAttribute("title", "Categories");

        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);

        return "category/categoriesList";
    }

    @GetMapping(value = "/createCategory")
    public String create(Model model) {

        model.addAttribute("title", "New Product");

        Category category = new Category();
        model.addAttribute("category", category);

        return "category/categoryCreate";
    }

    @PostMapping(value = "/saveCategory")
    public String save(@ModelAttribute Category category,
                       RedirectAttributes flash) {

        String flashmessage = "Your category has been created";

        categoryService.save(category);

        flash.addFlashAttribute("success", flashmessage);

        return "redirect:categoryList";
    }

    @GetMapping(value = "/editCategory/{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       Model model,
                       RedirectAttributes flash) {

        Category category = null;

        if (id > 0) {
            category = categoryService.findOne(id);
            if (category == null) {
                flash.addFlashAttribute("error", "The category does not exist");
                return "redirect:/product/productList";
            }

        } else {
            flash.addFlashAttribute("error", "The category does not exist");
            return "redirect:/product/productList";
        }

        model.addAttribute("category", category);

        return "category/categoryCreate";

    }

    @RequestMapping(value = "/deleteCategory/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         RedirectAttributes flash) {

        if (id > 0) {

            try {
                Category category = categoryService.findOne(id);

                if (category != null) {

                    if (category.getProducts().isEmpty()) {
                        categoryService.delete(id);
                        flash.addFlashAttribute("success", "The category has been deleted");
                    } else {
                        category.setStatus(CategoryStatus.REMOVED);
                        categoryService.save(category);
                        flash.addFlashAttribute("error", "The category is associated with one or more products and cannot be deleted.");
                    }
                }
            }catch (NullPointerException e) {
                return "redirect:/category/categoryList";
            }

        }

        return "redirect:/category/categoryList";

    }

}

