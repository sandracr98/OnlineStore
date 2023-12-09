package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.*;

import com.sandrajavaschool.OnlineStore.service.implService.ICategoryService;
import com.sandrajavaschool.OnlineStore.service.implService.IProductService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private RedirectAttributes flash;

    @Mock
    private IProductService productService;

    @Mock
    private ICategoryService categoryService;

    @InjectMocks
    private ProductController productController;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListEndpoint() {
        int page = 0;
        String term = "searchTerm";
        Pageable pageRequest = PageRequest.of(page, 4);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product());

        Page<Product> mockProducts = new org.springframework.data.domain.PageImpl<>(productList, pageRequest, 1);

        when(productService.findAll(pageRequest)).thenReturn(mockProducts);
        when(productService.findByName(term, pageRequest)).thenReturn(mockProducts);

        String viewName = productController.list(page, model, term, session);

        assertEquals("product/productsList", viewName);

        verify(productService, times(1)).findByName(term, pageRequest);

        if (term == null || term.isEmpty()) {
            verify(productService, times(1)).findAll(pageRequest);
        }
    }

    @Test
    void testCreateEndpoint() {
        Model model = mock(Model.class);

        List<Category> mockCategories = new ArrayList<>();
        when(categoryService.findAll()).thenReturn(mockCategories);

        String viewName = productController.create(model);

        assertEquals("product/productCreate", viewName);

        verify(model, times(1)).addAttribute(eq("title"), eq("New Product"));
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
        verify(model, times(1)).addAttribute(eq("categories"), eq(mockCategories));
        verify(categoryService, times(1)).findAll();

    }


    @Test
    public void testEditProductWhenIdIsGreaterThanZeroAndProductExists() {
        long productId = 1L;
        Product mockProduct = new Product();
        List<Category> mockCategories = new ArrayList<>();

        when(productService.findOne(productId)).thenReturn(mockProduct);
        when(categoryService.findAll()).thenReturn(mockCategories);

        String viewName = productController.edit(productId, model, flash);

        assertEquals("product/productCreate", viewName);

        verify(model, times(1)).addAttribute(eq("categories"), eq(mockCategories));
        verify(model, times(1)).addAttribute(eq("product"), eq(mockProduct));
        verify(flash, never()).addFlashAttribute(eq("error"), anyString());
        verify(flash, never()).addFlashAttribute(eq("success"), anyString());
        verify(productService, times(1)).findOne(productId);
        verify(categoryService, times(1)).findAll();
    }

    @Test
    public void testDeleteProductWhenIdIsGreaterThanZeroAndNoAssociatedProducts() {
        long productId = 1L;
        Product mockProduct = new Product();

        when(productService.findOne(productId)).thenReturn(mockProduct);

        String viewName = productController.delete(productId, flash);

        assertEquals("redirect:/productsList", viewName);

        verify(productService, times(1)).delete(productId);
        verify(productService, never()).save(any(Product.class));
        verify(flash, times(1)).addFlashAttribute(eq("success"), eq("The product has been deleted"));
        verify(flash, never()).addFlashAttribute(eq("error"), anyString());
    }

    @Test
    public void testDeleteProductWhenIdIsGreaterThanZeroAndAssociatedProductsExist() {
        long productId = 1L;
        Product mockProduct = new Product();

        ReceiptLine receiptLine = new ReceiptLine();
        receiptLine.setId(100L);

        when(productService.findOne(productId)).thenReturn(mockProduct);
        List<ReceiptLine> receiptLines = Collections.singletonList(receiptLine);
        mockProduct.setReceiptLine(receiptLines);

        String viewName = productController.delete(productId, flash);

        assertEquals("redirect:/productsList", viewName);

        verify(productService, never()).delete(productId);
        verify(productService, times(1)).save(mockProduct);
        verify(flash, never()).addFlashAttribute(eq("success"), anyString());
        verify(flash, times(1)).addFlashAttribute(eq("error"), eq("The product is associated with one or more products and cannot be deleted."));
    }

    @Test
    public void testDeleteProductWhenIdIsZero() {
        long productId = 0L;

        String viewName = productController.delete(productId, flash);

        assertEquals("redirect:/productsList", viewName);

        verify(productService, never()).findOne(productId);
        verify(productService, never()).delete(productId);
        verify(productService, never()).save(any(Product.class));
        verify(flash, never()).addFlashAttribute(eq("success"), anyString());
        verify(flash, never()).addFlashAttribute(eq("error"), anyString());
    }

}
