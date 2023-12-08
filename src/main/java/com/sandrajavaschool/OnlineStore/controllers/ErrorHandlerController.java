package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.errorsException.OrderNotFoundException;
import com.sandrajavaschool.OnlineStore.errorsException.ProductNotFoundException;
import com.sandrajavaschool.OnlineStore.errorsException.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController {

    //Con el controllerAdvice coge un error y la mapea a una excepcion no a una ruta,
    //Se encarga de todas general

    @ExceptionHandler({ArithmeticException.class})
    public String arithmeticError(ArithmeticException e, Model model) {
        model.addAttribute("error", "Arithmetic Exception");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("message2", "An arithmetic error occurred. Please check your calculations and ensure that you are not dividing by zero or performing an invalid arithmetic operation.");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error/arithmeticException";

    }

    @ExceptionHandler({NumberFormatException.class})
    public String numberFormatError(NumberFormatException e, Model model) {
        model.addAttribute("error", "Number Format Exception");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("message2", "An arithmetic error occurred. Please check your input values and ensure they are suitable for the intended arithmetic operation.");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error/numberFormatException";

    }

    @ExceptionHandler({UserNotFoundException.class})
    public String userNotFoundException(UserNotFoundException e, Model model) {
        model.addAttribute("error", "User not found");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("message2", "An unexpected error occurred. Please try again later or contact support if the issue persists.");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error/genericError";

    }

    @ExceptionHandler({OrderNotFoundException.class})
    public String orderNotFoundException(OrderNotFoundException e, Model model) {
        model.addAttribute("error", "Order not found");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("message2", "An unexpected error occurred. Please try again later or contact support if the issue persists.");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error/genericError";

    }

    @ExceptionHandler({ProductNotFoundException.class})
    public String productNotFoundException(ProductNotFoundException e, Model model) {
        model.addAttribute("error", "Product not found");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("message2", "An unexpected error occurred. Please try again later or contact support if the issue persists.");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error/genericError";

    }




}
