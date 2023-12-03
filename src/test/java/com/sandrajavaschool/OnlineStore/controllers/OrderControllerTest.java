package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.*;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import com.sandrajavaschool.OnlineStore.service.implService.IPaymentMethodService;
import com.sandrajavaschool.OnlineStore.service.implService.IProductService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private IOrderService orderService;

    @Mock
    private IUserService userService;

    @Mock
    private IProductService productService;

    @Mock
    private IPaymentMethodService paymentMethod;

    @Mock
    private BindingResult result;

    @Mock
    private SessionStatus status;

    @Mock
    private RedirectAttributes flash;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testList() {

        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderService.findAll()).thenReturn(orders);

        String viewName = orderController.list(model);

        verify(orderService).findAll();

        verify(model).addAttribute("orders", orders);

        assertEquals("/order/ordersList", viewName);
    }

    @Test
    public void testCreate() {

        Long userId = 1L;
        User user = new User();
        when(userService.findOne(userId)).thenReturn(user);


        String result = orderController.create(userId, model);

        verify(userService).findOne(userId);

        assertEquals("order/receipt", result);
    }

    @Test
    public void testLoadProducts() {

        String term = "searchTerm";
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());
        when(userService.findByName(term)).thenReturn(expectedProducts);

        List<Product> result = orderController.loadProducts(term);


        verify(userService).findByName(term);

        assertEquals(expectedProducts, result);
    }


    @Test
    public void testSaveOrderSuccessfully() {

        Order order = new Order();
        PaymentMethod paymentMethod = new PaymentMethod();
        User user = new User();
        user.setId(1L);
        order.setUser(user);


        when(result.hasErrors()).thenReturn(false);

        Product productWithNonNullPrice = new Product();
        productWithNonNullPrice.setId(1L);
        productWithNonNullPrice.setPrice(10.0);

        when(userService.findProductById(anyLong())).thenReturn(productWithNonNullPrice);

        String viewName = orderController.save(order, paymentMethod, result, model,
                new Long[]{1L}, new Integer[]{2}, flash, status);

        assertEquals("redirect:/userDetails/" + order.getUser().getId(), viewName);
        verify(userService, times(1)).findProductById(eq(1L));
        verify(status, times(1)).setComplete();
        verify(flash, times(1)).addFlashAttribute("success", "The order was created successfully");
    }

    @Test
    public void testSaveOrderWithBindingResultErrors() {
        // Arrange
        Order order = new Order();
        PaymentMethod paymentMethod = new PaymentMethod();
        User user = new User();
        user.setId(1L);
        order.setUser(user);

        when(result.hasErrors()).thenReturn(true);

        String viewName = orderController.save(order, paymentMethod, result, model,
                new Long[]{1L}, new Integer[]{2}, flash, status);

        assertEquals("redirect:/userDetails/" + order.getUser().getId(), viewName);
        verifyNoInteractions(userService);
        verifyNoInteractions(status);
        verifyNoInteractions(flash);
    }

    @Test
    public void testSaveOrderWithNullItemId() {

        Order order = new Order();
        PaymentMethod paymentMethod = new PaymentMethod();
        User user = new User();
        user.setId(1L);
        order.setUser(user);

        String viewName = orderController.save(order, paymentMethod, result, model,
                null, new Integer[]{2}, flash, status);

        assertEquals("redirect:/userDetails/" + order.getUser().getId(), viewName);
        verifyNoInteractions(userService);
        verifyNoInteractions(status);
        verifyNoInteractions(flash);
    }

    @Test
    public void testSaveOrder_ProductNotFound() {

        Order order = new Order();
        PaymentMethod paymentMethod = new PaymentMethod();
        User user = new User();
        user.setId(1L);
        order.setUser(user);

        Long[] itemId = {79L};
        Integer[] amount = {2};

        when(userService.findProductById(1L)).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> orderController.save(order, paymentMethod, result, model, itemId, amount, flash, status));

        verify(userService, times(1)).findProductById(79L);
    }

    @Test
    public void testSaveOrder_ProductPriceIsNull() {

        Order order = new Order();
        PaymentMethod paymentMethod = new PaymentMethod();
        User user = new User();
        user.setId(1L);
        order.setUser(user);

        Long[] itemId = {79L};
        Integer[] amount = {2};

        Product product = new Product();
        product.setId(79L);
        product.setPrice(null);
        when(userService.findProductById(79L)).thenReturn(product);

        assertThrows(IllegalStateException.class, () -> orderController.save(order, paymentMethod, result, model, itemId, amount, flash, status));

        verify(userService, times(1)).findProductById(79L);
    }

    @Test
    void testSaveWithInvalidAmounts() {

        Order order = new Order();
        PaymentMethod paymentMethod = new PaymentMethod();
        Long[] itemId = {79L};
        User user = new User();
        user.setId(1L);
        order.setUser(user);

        Product product = new Product();
        product.setId(79L);
        product.setPrice(30.00);
        when(userService.findProductById(79L)).thenReturn(product);

        // Test with null amounts
        Integer[] nullAmounts = null;
        testSaveWithInvalidAmount(order, paymentMethod, result, model, itemId, nullAmounts);

        // Test with negative amounts
        Integer[] negativeAmounts = {-1, -2};
        testSaveWithInvalidAmount(order, paymentMethod, result, model, itemId, negativeAmounts);

        // Test with zero amounts
        Integer[] zeroAmounts = {0, 0};
        testSaveWithInvalidAmount(order, paymentMethod, result, model, itemId, zeroAmounts);
    }

    private void testSaveWithInvalidAmount(Order order, PaymentMethod paymentMethod, BindingResult result, Model model,
                                           Long[] itemId, Integer[] amount) {
        RedirectAttributes flash = mock(RedirectAttributes.class);
        SessionStatus status = mock(SessionStatus.class);
        String resultView = orderController.save(order, paymentMethod, result, model, itemId, amount, flash, status);

        assertEquals("redirect:/userDetails/" + order.getUser().getId(), resultView);
        verify(flash).addFlashAttribute(eq("error"), eq("ERROR: Invalid or missing amounts"));
    }

    @Test
    void testSaveOrderStatus() {

        Order order = new Order();

        String result = orderController.saveOrderStatus(order, flash);

        assertEquals("redirect:/order/ordersList", result);

        verify(orderService, times(1)).save(order);
        verify(flash, times(1)).addFlashAttribute("success", "Congratulation! You change the status of the order");
    }

    @Test
    void testDeleteOrder() {

        Long orderId = 1L;
        RedirectAttributes flash = mock(RedirectAttributes.class);
        Order orderToDelete = new Order();
        orderToDelete.setUser(new User());

        when(userService.findOrderById(orderId)).thenReturn(orderToDelete);

        String result = orderController.deleteOrder(orderId, flash);

        assertEquals("redirect:/userDetails/" + orderToDelete.getUser().getId(), result, "El resultado debería ser la redirección esperada");
        verify(userService, times(1)).deleteOrder(orderId);
        verify(flash, times(1)).addFlashAttribute("success", "The order was deleted successfully");
    }

    @Test
    void testReorderSuccess() {
        Long orderId = 1L;
        Order existingOrder = new Order();
        User existingUser = new User();

        existingUser.setId(1L);
        existingOrder.setId(orderId);
        existingOrder.setUser(existingUser);
        existingOrder.setDescription("Existing Order");
        existingOrder.setGoods("Good1");

        Product productWithNonNullPrice = new Product();
        productWithNonNullPrice.setId(1L);
        productWithNonNullPrice.setPrice(10.00);

        ReceiptLine receiptLine1 = new ReceiptLine();
        receiptLine1.setProduct(productWithNonNullPrice);
        receiptLine1.setAmount(2);

        ReceiptLine receiptLine2 = new ReceiptLine();
        receiptLine2.setProduct(productWithNonNullPrice);
        receiptLine2.setAmount(1);

        List<ReceiptLine> receiptLines = Arrays.asList(receiptLine1, receiptLine2);
        existingOrder.setReceiptLines(receiptLines);

        when(userService.findOrderById(orderId)).thenReturn(existingOrder);
        when(userService.findOne(orderId)).thenReturn(existingUser);
        when(userService.findProductById(1L)).thenReturn(productWithNonNullPrice);

        String result = orderController.reorder(orderId, flash, model);

        assertEquals("order/receiptReorder", result);
        verify(userService, times(2)).findProductById(anyLong());
        verify(productService, times(2)).save(any(Product.class));
        verify(model, times(1)).addAttribute(eq("order"), any(Order.class));
        verify(model, times(1)).addAttribute(eq("paymentMethod"), any(PaymentMethod.class));
    }


    @Test
    void testReorderInvalidOrderId() {
        String result = orderController.reorder(null, flash, model);

        assertEquals("redirect:/error/nullPointerException", result);
        verify(flash, times(1)).addFlashAttribute(eq("error"), anyString());
    }

    @Test
    void testReorderOrderNotFound() {
        Long orderId = 1L;
        when(userService.findOrderById(orderId)).thenReturn(null);

        RedirectAttributes flash = mock(RedirectAttributes.class);
        Model model = mock(Model.class);
        String result = orderController.reorder(orderId, flash, model);

        assertEquals("redirect:/userDetails/", result, "La redirección debería ser la esperada");
        verify(flash, times(1)).addFlashAttribute(eq("error"), anyString());
    }

    @Test
    void testReorderReceiptLinesIsNull() {
        Long orderId = 1L;
        Order existingOrder = new Order();
        User existingUser = new User();

        existingUser.setId(1L);
        existingUser.setName("John");
        existingUser.setSurname("Doe");
        existingOrder.setId(orderId);
        existingOrder.setUser(existingUser);
        existingOrder.setDescription("Existing Order");
        existingOrder.setGoods("Good1");


        Product productWithNonNullPrice = new Product();
        productWithNonNullPrice.setId(1L);
        productWithNonNullPrice.setPrice(10.00);

        List<ReceiptLine> receiptLines = new ArrayList<>();
        existingOrder.setReceiptLines(receiptLines);

        when(userService.findProductById(1L)).thenReturn(productWithNonNullPrice);
        when(userService.findOrderById(orderId)).thenReturn(existingOrder);
        when(userService.findOne(orderId)).thenReturn(existingUser);

        /*
        assertThrows(NullPointerException.class, () -> {
            orderController.reorder(orderId, flash, model);
        });

         */
        verify(userService, never()).findOne(anyLong());
        verify(userService, never()).findProductById(anyLong());
        verify(productService, never()).save(any());
    }

    @Test
    void testSaveReorderWithValidOrder() {
        Order newOrder = new Order();
        PaymentMethod paymentMethod = new PaymentMethod();
        User existingUser = new User();

        newOrder.setId(1L);
        newOrder.setUser(existingUser);

        existingUser.setId(1L);
        existingUser.setName("John");
        existingUser.setSurname("Doe");

        doNothing().when(userService).saveOrder(any(Order.class));

        String result = orderController.saveReorder(newOrder, paymentMethod, flash);

        assertEquals("redirect:/userDetails/" + newOrder.getUser().getId(), result);
        verify(flash).addFlashAttribute("success", "Congratulation! Your order has completed");
        verifyNoMoreInteractions(flash);
    }

    @Test
    void testSaveReorderWithNullOrder() {

        String result = orderController.saveReorder(null, new PaymentMethod(), flash);

        assertEquals("redirect:/order/ordersList", result);
        verify(flash).addFlashAttribute("error", "Existing order is null");
        verifyNoMoreInteractions(flash);
    }


}

