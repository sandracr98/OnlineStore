package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.*;
import com.sandrajavaschool.OnlineStore.errorsException.OrderNotFoundException;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import com.sandrajavaschool.OnlineStore.service.implService.IPaymentMethodService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
@RequiredArgsConstructor
public class OrderController {

    final private IUserService userService;
    final private IOrderService orderService;
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
            return "redirect:/order/receipt/anonymous";
        }
    }

    @GetMapping("/receipt/anonymous")
    public String createNewOrder(Model model) {

        Order order = new Order();

        PaymentMethod paymentMethod = new PaymentMethod();
        order.setPaymentMethod(paymentMethod);

        model.addAttribute("order", order);
        model.addAttribute("paymentMethod", paymentMethod);

        model.addAttribute("title", "Shopping cart");

        return "order/receiptNewUser";
    }

    @PostMapping("/createUserBeforeReceipt")
    public String saveUserAndReceipt() {

        return "";
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

//preguntar @RestController y el @ResponseBody

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

            ReceiptLine line = new ReceiptLine();

            line.setAmount(amount[i]);
            line.setProduct(product);

            order.addReceiptLine(line);
        }

        Double total = order.getTotal();
        order.setSum(total);

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

        //¿Debo dejarlo así o como siempre lo he hecho, se suele usar asi cuando
        // trabajamos con un objeto entity con muchas relaciones?

        //Order order = userService.fetchByIdWithUserReceiptLineProduct(id);

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

            return "order/orderDetails"; // Ajusta según tu configuración de vistas
        } catch (OrderNotFoundException e) {
            // Manejar la excepción, por ejemplo, redirigir a una página de error
            return "errorPage"; // Ajusta según tu configuración de vistas
        }
    }

    @PostMapping("/saveOrderDetails")
    public String saveOrderStatus(@Valid Order order,
                                  RedirectAttributes flash) {
        String flashmessage = "Congratulation! You change status order";

        orderService.save(order);

        flash.addFlashAttribute("success", flashmessage);

        return "redirect:/order/ordersList";
    }


    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable(value = "id") Long id,
                              RedirectAttributes flash) {
        Order order = userService.findOrderById(id);

        if (order != null) {
            userService.deleteOrder(id);
            flash.addFlashAttribute("success", "The order was deleted successfully");
            return "redirect:/userDetails/" + order.getUser().getId();
        }

        flash.addFlashAttribute("error", "The order does not exist in DDBB");
        return "redirect:/list/";
    }


    @RequestMapping("/reorder/{id}")
    public String reorder(@PathVariable(value = "id") Long id,
                          RedirectAttributes flash,
                          Model model) {
        try {
            if (id == null || id <= 0) {
                flash.addFlashAttribute("error", "Invalid order ID");
                return "redirect:/list"; //INSERTAR PAGINA DE ERROR
            }

            Order existingOrder = userService.findOrderById(id);

            if (existingOrder == null || userService.findOne(id) == null) {
                flash.addFlashAttribute("error", "Order does not exist in the database");
                assert existingOrder != null;
                return "redirect:/userDetails/" + existingOrder.getUser().getId();
            }

            Order newOrder = new Order();
            newOrder.setUser(existingOrder.getUser());

            PaymentMethod paymentMethod = new PaymentMethod();
            newOrder.setPaymentMethod(paymentMethod);

            newOrder.setDescription(existingOrder.getDescription());
            newOrder.setGoods(existingOrder.getGoods());

            List<ReceiptLine> receiptLines = existingOrder.getReceiptLines();

            for (ReceiptLine receiptLine : receiptLines) {

                Product product = userService.findProductById(receiptLine.getProduct().getId());

                ReceiptLine line = new ReceiptLine();

                line.setAmount(receiptLine.getAmount());
                line.setProduct(product);

                newOrder.addReceiptLine(line);
            }

            Double total = newOrder.getTotal();
            newOrder.setSum(total);


            model.addAttribute("order", newOrder);
            model.addAttribute("paymentMethod", paymentMethod);

            return "order/receiptReorder";

        } catch (Exception e) {
            logger.error("Error: " + e.getMessage(), e);
            flash.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/error/error403";
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


        try {

            userService.saveOrder(newOrder);

            String flashmessage = "Congratulation! Your order has completed";


            flash.addFlashAttribute("success", flashmessage);

            return "redirect:/userDetails/" + newOrder.getUser().getId();


        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error saving order: " + e.getMessage());
            return "/error/error403";
        }

    }
}
