package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.*;
import com.sandrajavaschool.OnlineStore.errorsException.OrderNotFoundException;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import com.sandrajavaschool.OnlineStore.service.implService.IPaymentMethodService;
import com.sandrajavaschool.OnlineStore.service.implService.IProductService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
@RequiredArgsConstructor
public class OrderController {

    final private IUserService userService;
    final private IOrderService orderService;
    final private IProductService productService;
    final private IPaymentMethodService paymentMethodService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/ordersList")
    public String list(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);

        return "/order/ordersList";
    }

    @GetMapping("/receiptControl")
    public String showReceipt(Principal principal) {
        if (principal != null) {
            // Authenticated user, obtain their ID and redirect to their shopping cart
            String email = principal.getName();
            User user = userService.findByEmail(email);
            Long userId = user.getId();
            return "redirect:/order/receipt/" + userId;
        } else {
            // Unauthenticated user, (principal == null) redirect to the creation of a cart not linked to a user
            return "order/receiptNewUser";
        }
    }


    @GetMapping("/receipt/{userId}")
    public String create(@PathVariable(value = "userId") Long userId,
                         Model model) {

        // 1. Retrieve the User object using the userService with the provided ID.
        User user = userService.findOne(userId);

        // 2. Create an Order object.
        Order order = new Order();
        // 3. Assign the user to the Order object, establishing the relationship between the receipt and the customer.
        order.setUser(user); //de esta forma asignamos una factura con un cliente

        // 4. Create a PaymentMethod object and assign it to the Order object.
        PaymentMethod paymentMethod = new PaymentMethod();
        order.setPaymentMethod(paymentMethod);

        model.addAttribute("order", order);
        model.addAttribute("paymentMethod", paymentMethod);

        model.addAttribute("title", "Shopping cart");

        return "order/receipt";
    }

    @GetMapping(value = "/load-products/{term}", produces = {"application/json"})
    public @ResponseBody List<Product> loadProducts(@PathVariable String term) {
        return userService.findByName(term);
    }

    @PostMapping("/receipt")
    public String save(@Valid Order order,
                       @Valid @ModelAttribute PaymentMethod paymentMethod,
                       BindingResult result,
                       Model model,
                       @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                       @RequestParam(name = "amount[]", required = false) Integer[] amount,
                       RedirectAttributes flash,
                       SessionStatus status) {

        // 1. Check for validation errors in the Order object
        if (result.hasErrors()) {
            model.addAttribute("title", "Create Order");
            return "redirect:/userDetails/" + order.getUser().getId();
        }

        // 2. Check if the receipt is blank
        if (itemId == null || itemId.length == 0) {
            model.addAttribute("title", "Create Order");
            model.addAttribute("error", "ERROR: the receipt is blank");
            return "redirect:/userDetails/" + order.getUser().getId();
        }


        // 3. Iterate over items in the receipt
        for (int i = 0; i < itemId.length; i++) {

            // 3.1 Retrieve product information based on item ID
            Product product = userService.findProductById(itemId[i]);

            // 3.2 Check for null product or null product price
            if (product == null) {
                throw new IllegalStateException("Product is null for product ID: " + itemId[i]);
            }

            if (product.getPrice() == null) {
                throw new IllegalStateException("Product price is null for this product");
            }

            // 3.3 Check for invalid or missing amounts
            if (amount == null || Arrays.stream(amount).anyMatch(value -> value == null || value <= 0)) {
                flash.addFlashAttribute("error", "ERROR: Invalid or missing amounts");
                return "redirect:/userDetails/" + order.getUser().getId();
            }

            // 3.4 Create a new ReceiptLine and populate it with information
            ReceiptLine line = new ReceiptLine();

            line.setAmount(amount[i]);
            line.setProduct(product);

            // 3.5 Add the ReceiptLines to the Order
            order.addReceiptLine(line);

            // 3.6 Update product total sales and save the product
            product.setTotalSales(product.getTotalSales() + amount[i]);
            productService.save(product);

        }

        // 4. Set the order total with rounded value
        BigDecimal total = BigDecimal.valueOf(order.getTotal());
        BigDecimal roundedTotal = total.setScale(2, BigDecimal.ROUND_HALF_UP);
        order.setSum(roundedTotal.doubleValue());

        // 5. Save the payment method
        paymentMethodService.save(paymentMethod);

        // 6. Save the order
        userService.saveOrder(order);

        status.setComplete();

        // 8. Set success flash attribute and redirect to user details
        flash.addFlashAttribute("success", "The order was created successfully");
        return "redirect:/userDetails/" + order.getUser().getId();
    }


    @GetMapping("/viewReceipt/{id}")
    public String viewOrder(@PathVariable(value = "id") Long id,
                            Model model) {

        // 1. Retrieve the order based on the provided ID
        Order order = userService.findOrderById(id);

        // 2. Check if the order exists; if not, throw an exception
        if (order == null) {
            throw new OrderNotFoundException("Order does not exist");
        }

        // 3. Add the order and title attributes to the model for rendering in the view
        model.addAttribute("order", order);
        model.addAttribute("title", "Order: ".concat(order.getId().toString()));

        // 4. Return the view name for displaying order details
        return "order/viewOrderDetails";

    }

    @GetMapping("/orderDetails/{id}")
    public String editOrder(@PathVariable(value = "id") Long id,
                            Model model) {

        try {
            // 1. Attempt to retrieve the order based on the provided ID
            Order order = orderService.findOne(id);

            // 2. Add the order and title attributes to the model for rendering in the view
            model.addAttribute("order", order);
            model.addAttribute("title", "Order Details");

            // 3. Return the view name for displaying order details
            return "order/orderDetails";

        } catch (Exception e) {
            // 4. If an exception occurs (e.g., order not found), throw OrderNotFoundException
            throw new OrderNotFoundException("Order does not exist");
        }
    }

    @PostMapping("/saveOrderDetails")
    public String saveOrderStatus(@Valid Order order,
                                  RedirectAttributes flash) {

        String flashMessage = "Congratulation! You change the status of the order";

        try {
            orderService.save(order);
            flash.addFlashAttribute("success", flashMessage);

        } catch (Exception e) {
            throw new OrderNotFoundException("An error occurred while saving the order");
        }

        return "redirect:/order/ordersList";
    }


    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable(value = "id") Long id,
                              RedirectAttributes flash) {
        try {
            // 1. Attempt to retrieve the order based on the provided ID
            Order order = userService.findOrderById(id);

            // 2. Delete the order using the userService
            userService.deleteOrder(id);

            flash.addFlashAttribute("success", "The order was deleted successfully");
            return "redirect:/userDetails/" + order.getUser().getId();

        } catch (Exception e) {
            // 4. If an exception occurs, add error flash attribute and redirect to the list page
            flash.addFlashAttribute("error", "The order does not exist in the database");
            return "redirect:/list/";
        }

    }


    @RequestMapping("/reorder/{id}")
    public String reorder(@PathVariable(value = "id") Long id,
                          RedirectAttributes flash,
                          Model model) {
        try {

            // 1. Check for invalid order ID
            if (id == null || id <= 0) {
                flash.addFlashAttribute("error", "Invalid order ID");
                return "redirect:/error/nullPointerException";
            }

            // 2. Retrieve the existing order based on the provided ID
            Order existingOrder = userService.findOrderById(id);

            // 3. Check if the existing order exists
            if (existingOrder == null || userService.findOne(id) == null) {
                flash.addFlashAttribute("error", "Order does not exist in the database");
                return "redirect:/userDetails/" + (existingOrder != null ? existingOrder.getUser().getId() : "");
            }

            // 4. Create a new order and populate it with user and goods information from the existing order
            Order newOrder = new Order();
            newOrder.setUser(existingOrder.getUser());
            newOrder.setGoods(existingOrder.getGoods());

            // 5. Create a new PaymentMethod for the new order
            PaymentMethod paymentMethod = new PaymentMethod();
            newOrder.setPaymentMethod(paymentMethod);

            // 6. Retrieve receipt lines from the existing order
            List<ReceiptLine> receiptLines = existingOrder.getReceiptLines();

            // 7. Iterate over receipt lines and populate the new order with products and amounts
            if (receiptLines != null) {

                for (ReceiptLine receiptLine : receiptLines) {

                    if (receiptLine.getProduct() != null) {

                        Product product = userService.findProductById(receiptLine.getProduct().getId());

                        ReceiptLine line = new ReceiptLine();

                        line.setAmount(receiptLine.getAmount());
                        line.setProduct(product);

                        newOrder.addReceiptLine(line);

                        product.setTotalSales(product.getTotalSales() + line.getAmount());
                        productService.save(product);

                    } else {
                        // Handle the case where product is null for a ReceiptLine
                        flash.addFlashAttribute("error", "Error: Product is null for ReceiptLine");
                        return "redirect:/error/nullPointerException";
                    }
                }

            } else {
                // Handle the case where receipt lines are null
                flash.addFlashAttribute("error", "Error: Product is null for ReceiptLine");
                return "redirect:/error/nullPointerException";
            }


            // 8. Set the total and sum for the new order
            Double total = newOrder.getTotal();
            newOrder.setSum(total);

            // 9. Add order and paymentMethod attributes to the model for rendering in the view
            model.addAttribute("order", newOrder);
            model.addAttribute("paymentMethod", paymentMethod);

            // 10. Return the view name for displaying the reordered order details
            return "order/receiptReorder";


        } catch (Exception e) {
            // 11. Handle any exception that may occur and log an error
            logger.error("Error: " + e.getMessage(), e);
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/error/error405";
        }

    }


    @PostMapping("/saveReorder")
    public String saveReorder(@ModelAttribute Order newOrder,
                              @ModelAttribute PaymentMethod paymentMethod,
                              RedirectAttributes flash) {

        // 1. Check if the new order is null
        if (newOrder == null) {
            flash.addFlashAttribute("error", "Existing order is null");
            return "redirect:/order/ordersList";
        }

        // 2. Save the payment method
        paymentMethodService.save(paymentMethod);

        // 3. Save the new order
        userService.saveOrder(newOrder);

        String flashmessage = "Congratulation! Your order has completed";
        flash.addFlashAttribute("success", flashmessage);

        return "redirect:/userDetails/" + newOrder.getUser().getId();


    }
}
