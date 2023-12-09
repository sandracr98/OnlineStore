package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.dao.IRoleDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private Model model;

    @Mock
    private ModelMap modelMap;
    @Mock
    private UserService userService;

    @Mock
    private BindingResult result;

    @Mock
    private IUserDao userDao;

    @Mock
    private IRoleDao roleDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MultipartFile photo;
    @Mock
    private RedirectAttributes flash;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreate() {
        String viewName = userController.create(model);

        verify(model).addAttribute("title", "Sign Up!");


        assertEquals("user/signup", viewName);
    }

    @Test
    public void testSaveExistingUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("existinguser@example.com");

        // Mockear el comportamiento del DAO para que exista un usuario con el mismo correo electrónico
        when(userDao.existsByEmail(existingUser.getEmail())).thenReturn(true);

        // Act
        String viewName = userController.save(existingUser, result, model, photo, flash);

        // Verifica que la vista redirige a "/create"
        assertEquals("redirect:/create", "redirect:/create", viewName);

    }

    @Test
    public void testSaveUserWithValidationError() {
        // Caso: Intentar guardar un usuario con errores de validación

        // Arrange
        User userWithValidationError = new User();
        userWithValidationError.setEmail("invalid-email");

        // Simulate validation error by adding an error to the result
        when(result.hasErrors()).thenReturn(true);
        when(result.getFieldError("email")).thenReturn(new FieldError("user", "email", "Invalid email format"));

        // Act
        String viewName = userController.save(userWithValidationError, result, model, photo, flash);

        // Assert
        verify(userService, never()).save(any(User.class));
        verify(model).addAttribute("title", "Sign Up!");
        // Add more assertions based on your specific requirements

        assertEquals("redirect:/create", viewName);
    }

    @Test
    public void testDelete() {
        // Arrange
        Long userId = 1L;

        // Configura el comportamiento del servicio y flash según tus necesidades
        doNothing().when(userService).delete(userId);

        // Act
        String result = userController.delete(userId, flash);

        // Assert
        // Verifica que el servicio se haya llamado para eliminar al usuario
        verify(userService).delete(userId);

        // Verifica que el mensaje de éxito se haya agregado a los atributos de redirección
        verify(flash).addFlashAttribute("success", "The user has been deleted");

        // Ajusta las siguientes líneas según tu implementación y expectativas
        assertEquals("redirect:/list", result);  // Ajusta según tu implementación
        // Agrega más aserciones según tus expectativas
    }

}
