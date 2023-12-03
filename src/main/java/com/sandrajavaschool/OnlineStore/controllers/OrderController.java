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

    //////////////////////////INTENTO DE HACER EL ORDER SIN LOGEARSE


    //CONTROLA SI ESTA LOGUEADO O NO
    @GetMapping("/receiptControl")
    public String showReceipt(Principal principal) {
        if (principal != null) {
            // Usuario autenticado, obtener su ID y redirigir a su carrito de compras
            String email = principal.getName();
            User user = userService.findByEmail(email);
            Long userId = user.getId();
            return "redirect:/order/receipt/" + userId;
        } else {
            // Usuario no autenticado, redirigir a la creación de un carrito no vinculado a un usuario
            return "order/receiptNewUser";
        }
    }


    ///////////////////////////////////////////////////////////////

    @GetMapping("/receipt/{userId}")
    public String create(@PathVariable(value = "userId") Long userId,
                         Model model) {

        User user = userService.findOne(userId);

        Order order = new Order();
        order.setUser(user); //de esta forma asignamos una factura con un cliente

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

        //Añadimos en messages.properties un mensaje de validacion, y modificamos la vista
        //receipt, para que nuestros campos esten validados si la descripcion es null

        if (result.hasErrors()) {
            model.addAttribute("title", "Create Order");
            return "redirect:/userDetails/" + order.getUser().getId();
        }


        //Si el id no existe o no hay cantidades de productos lanza esta validacion

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("title", "Create Order");
            model.addAttribute("error", "ERROR: the receipt is blank");
            return "redirect:/userDetails/" + order.getUser().getId();
        }


        for (int i = 0; i < itemId.length; i++) {

            Product product = userService.findProductById(itemId[i]);

            if (product == null) {
                throw new IllegalStateException("Product is null for product ID: " + itemId[i]);
            }

            if (product.getPrice() == null) {
                throw new IllegalStateException("Product price is null for this product");
                //puedo redirigir a nullPointerException.html
            }

            if (amount == null || Arrays.stream(amount).anyMatch(value -> value == null || value <= 0)) {
                flash.addFlashAttribute("error", "ERROR: Invalid or missing amounts");
                return "redirect:/userDetails/" + order.getUser().getId();
            }

            ReceiptLine line = new ReceiptLine();

            line.setAmount(amount[i]);
            line.setProduct(product);

            order.addReceiptLine(line);

            product.setTotalSales(product.getTotalSales() + amount[i]);
            productService.save(product);

        }

       /* Double total = order.getTotal();
        order.setSum(total);
        */

        BigDecimal total = BigDecimal.valueOf(order.getTotal());
        BigDecimal roundedTotal = total.setScale(2, BigDecimal.ROUND_HALF_UP);
        order.setSum(roundedTotal.doubleValue());

        paymentMethodService.save(paymentMethod);

        userService.saveOrder(order);

        status.setComplete();

        flash.addFlashAttribute("success", "The order was created successfully");
        return "redirect:/userDetails/" + order.getUser().getId();
    }


    @GetMapping("/viewReceipt/{id}")
    public String viewOrder(@PathVariable(value = "id") Long id,
                            Model model,
                            RedirectAttributes flash) {

        Order order = userService.findOrderById(id);

        if (order == null) {
            flash.addFlashAttribute("error", "Order does not exist into DDBB");
            return "redirect:/list";
        }

        model.addAttribute("order", order);
        model.addAttribute("title", "Order: ".concat(order.getDescription()));

        return "order/viewOrderDetails";

    }

    @GetMapping("/orderDetails/{id}")
    public String editOrder(@PathVariable(value = "id") Long id,
                            Model model,
                            RedirectAttributes flash) {

        try {
            Order order = orderService.findOne(id);
            if (order == null) {
                throw new OrderNotFoundException("Order with id " + id + " not found");
            }

            model.addAttribute("order", order);
            model.addAttribute("title", "Order Details");

            return "order/orderDetails";

        } catch (OrderNotFoundException e) {
            return "error/nullPointerExceptions";
        }
    }

    @PostMapping("/saveOrderDetails")
    public String saveOrderStatus(@Valid Order order,
                                  RedirectAttributes flash) {
        String flashMessage = "Congratulation! You change the status of the order";

        try {
            orderService.save(order);
            flash.addFlashAttribute("success", flashMessage);
        } catch (OrderNotFoundException e) {
            flash.addFlashAttribute("error", "An error occurred while saving the order: " + e.getMessage());
        }

        return "redirect:/order/ordersList";
    }


    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable(value = "id") Long id,
                              RedirectAttributes flash) {
        try {
            Order order = userService.findOrderById(id);
            userService.deleteOrder(id);
            flash.addFlashAttribute("success", "The order was deleted successfully");
            return "redirect:/userDetails/" + order.getUser().getId();
        } catch (OrderNotFoundException e) {
            flash.addFlashAttribute("error", "The order does not exist in the database");
            return "redirect:/list/";
        }

    }


    @RequestMapping("/reorder/{id}")
    public String reorder(@PathVariable(value = "id") Long id,
                          RedirectAttributes flash,
                          Model model) {
        try {

            if (id == null || id <= 0) {
                flash.addFlashAttribute("error", "Invalid order ID");
                return "redirect:/error/nullPointerException";
            }

            Order existingOrder = userService.findOrderById(id);

            if (existingOrder == null || userService.findOne(id) == null) {
                flash.addFlashAttribute("error", "Order does not exist in the database");
                return "redirect:/userDetails/" + (existingOrder != null ? existingOrder.getUser().getId() : "");
            }

            Order newOrder = new Order();
            newOrder.setUser(existingOrder.getUser());


            PaymentMethod paymentMethod = new PaymentMethod();
            newOrder.setPaymentMethod(paymentMethod);

            newOrder.setGoods(existingOrder.getGoods());


            List<ReceiptLine> receiptLines = existingOrder.getReceiptLines();

            if (receiptLines != null) {

                for (ReceiptLine receiptLine : receiptLines) {

                    if (receiptLine != null && receiptLine.getProduct() != null) {

                        Product product = userService.findProductById(receiptLine.getProduct().getId());

                        ReceiptLine line = new ReceiptLine();

                        line.setAmount(receiptLine.getAmount());
                        line.setProduct(product);

                        newOrder.addReceiptLine(line);

                        product.setTotalSales(product.getTotalSales() + line.getAmount());
                        productService.save(product);

                    } else {

                        flash.addFlashAttribute("error", "Error: Product is null for ReceiptLine");
                        return "redirect:/error/nullPointerException";
                    }
                }
            } else {
                flash.addFlashAttribute("error", "Error: Product is null for ReceiptLine");
                return "redirect:/error/nullPointerException";
            }


            Double total = newOrder.getTotal();
            newOrder.setSum(total);


            model.addAttribute("order", newOrder);
            model.addAttribute("paymentMethod", paymentMethod);

            return "order/receiptReorder";


        } catch (NullPointerException e) {
            logger.error("Error: " + e.getMessage(), e);
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/error/error405";
        }

    }


    @PostMapping("/saveReorder")
    public String saveReorder(@ModelAttribute Order newOrder,
                              @ModelAttribute PaymentMethod paymentMethod,
                              RedirectAttributes flash) {

        if (newOrder == null) {
            flash.addFlashAttribute("error", "Existing order is null");
            return "redirect:/order/ordersList";
        }

        userService.saveOrder(newOrder);
        String flashmessage = "Congratulation! Your order has completed";
        flash.addFlashAttribute("success", flashmessage);

        return "redirect:/userDetails/" + newOrder.getUser().getId();


    }
}
