package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.entities.Order;
import com.sandrajavaschool.OnlineStore.entities.Product;
import com.sandrajavaschool.OnlineStore.entities.ReceiptLine;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.implService.IOrderService;
import com.sandrajavaschool.OnlineStore.service.implService.IUserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
@RequiredArgsConstructor
public class OrderController {

    final private IUserService userService;

    @GetMapping("receipt/{userId}")
    public String create(@PathVariable(value = "userId") Long userId,
                         Model model) {

        User user = userService.findOne(userId);

        Order order = new Order();
        order.setUser(user); //de esta forma asignamos una factura con un cliente

        model.addAttribute("order", order);
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
            return "order/receipt";
        }

        //Si el id no existe o no hay cantidades de productos lanza esta validacion

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("title", "Create Order");
            model.addAttribute("error", "ERROR: the receipt is blank");
            return "order/receipt";
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

        userService.saveOrder(order);

        status.setComplete();

        flash.addFlashAttribute("success", "The order was created successfully");
        return "redirect:/userDetails/" + order.getUser().getId();
    }

    @GetMapping("/viewReceipt/{id}")
    public String viewOrder(@PathVariable(value = "id") Long id,
                            Model model,
                            RedirectAttributes flash) {

        //Order order = userService.findOrderById(id);

        //¿Debo dejarlo así o como siempre lo he hecho, se suele usar asi cuando
        // trabajamos con un objeto entity con muchas relaciones?

        Order order = userService.fetchByIdWithUserReceiptLineProduct(id);

        if (order == null) {
            flash.addFlashAttribute("error", "Order does not exist into DDBB");
            return "redirect:/list";
        }

        model.addAttribute("order", order);
        model.addAttribute("title", "Order: " .concat(order.getDescription()));

        return "order/viewOrderDetails";

    }


    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id,
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
}
