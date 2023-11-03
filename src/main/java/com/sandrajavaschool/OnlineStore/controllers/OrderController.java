package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.*;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import com.sandrajavaschool.OnlineStore.service.implService.IPaymentMethodService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
@RequiredArgsConstructor
public class OrderController {

    final private IUserService userService;
    final private IOrderService orderService;
    final private IPaymentMethodService paymentMethodService;

    @GetMapping("/ordersList")
    public String list(Model model) {
       List<Order> orders = orderService.findAll();
       model.addAttribute("orders", orders);

       return "/order/ordersList";
    }

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
        model.addAttribute("title", "Order: " .concat(order.getDescription()));

        return "order/viewOrderDetails";

    }

    @GetMapping("/orderDetails/{id}")
    public String editOrder(@PathVariable(value = "id") Long id,
                            Model model,
                            RedirectAttributes flash) {

        Order order = orderService.findOne(id);

        if (order == null) {
            flash.addFlashAttribute("error", "Order does not exist into DDBB");
            return "redirect:/list";
        }

        model.addAttribute("order", order);
        model.addAttribute("title", "Edit Order");

        return "order/orderDetails";
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
                          RedirectAttributes flash) {

        Order existingOrder = userService.findOrderById(id);

        if (existingOrder == null) {
            flash.addFlashAttribute("error", "Order does not exist into DDBB");
            return "redirect:/list";
        }

        Order newOrder = new Order();

        newOrder.setUser(existingOrder.getUser());
        newOrder.setDescription(existingOrder.getDescription());
        newOrder.setGoods(existingOrder.getGoods());

        existingOrder.setReceiptLines(null);
//LO SUYO ES QUE LO LLEVARA A CREAR UNA NUEVA ORDER EN CREAR PERO CON LOS PRODUCTOS YA HECHOS
// SOLO CAMBIAS 4 VARIABLES LOCAAS
        newOrder.setReceiptLines(existingOrder.getReceiptLines());

        orderService.save(newOrder);

        return "redirect:/userDetails/" + existingOrder.getUser().getId();

    }
}
