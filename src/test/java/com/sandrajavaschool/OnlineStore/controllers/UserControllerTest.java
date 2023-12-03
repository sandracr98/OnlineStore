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
    public void testEditUserExists() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setSurname("testuser");

        // Mockear el comportamiento del servicio para retornar el usuario existente
        when(userService.findOne(userId)).thenReturn(existingUser);

        // Act
        String viewName = userController.edit(userId, modelMap, flash);

        // Assert
        // Verifica que el modelo tenga los atributos esperados
        verify(modelMap).put("title", "Profile");
        verify(modelMap).put("user", existingUser);

        // Verifica que el servicio userService fue llamado con el ID correcto
        verify(userService, times(1)).findOne(userId);

        // Verifica que la vista devuelta sea la correcta
        assertEquals("user/signup", viewName);
    }

    @Test
    public void testEditUserNotExists() {
        Long userId = 1L;
        // Mockear el comportamiento del servicio para retornar null (usuario no existe)
        when(userService.findOne(userId)).thenReturn(null);

        // Act
        String viewName = userController.edit(userId, modelMap, flash);

        // Assert
        // Verifica que flash tenga el atributo de error
        verify(flash).addFlashAttribute("error", "The user does not exist");

        // Verifica que la vista devuelta sea la correcta (redirect:/usersList)
        assertEquals("redirect:/usersList", viewName);

        // Verifica que el servicio userService fue llamado con el ID correcto
        verify(userService, times(1)).findOne(userId);
    }

    @Test
    public void testSaveNewUser() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("newuser@example.com");

        // Mockear el comportamiento del servicio y el DAO
        when(userDao.existsByEmail(newUser.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(newUser.getPass())).thenReturn("hashedPassword");
        when(roleDao.findByName("ROLE_USER")).thenReturn(new Role());

        // Act
        String viewName = userController.save(newUser, result, model, photo, flash);

        // Assert
        verify(userService, times(1)).saveInternalPhoto(photo, newUser);
        verify(userService, times(1)).save(newUser);
        verify(model).addAttribute("success", "Congratulation! You have an account");

        assertEquals("redirect:/userDetails/" + newUser.getId(), viewName);
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
    public void testSaveNewUserWithExistingEmail() {
        // Caso: Intentar guardar un nuevo usuario con un correo electrónico que ya existe

        when(userDao.existsByEmail(anyString())).thenReturn(true);

        // Crea un usuario para la prueba
        User user = new User();
        user.setEmail("existing.email@example.com");

        // Ejecuta el método del controlador
        String conclusion = userController.save(user, result, model, null, flash );

        // Verifica que el método de servicio no se haya llamado
        verify(userService, never()).save(any(User.class));

        // Verifica que el modelo tenga el atributo de error
        assertTrue(conclusion.startsWith("redirect:/create"));

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
    public void testSaveUserWithExistingId() {
        // Caso: Intentar guardar un usuario con un ID existente (debería actualizarse en lugar de crear uno nuevo)

        // Arrange
        User userWithExistingId = new User();
        userWithExistingId.setId(1L);

        // Mockear el comportamiento del servicio y el DAO
        when(userService.findOne(userWithExistingId.getId())).thenReturn(userWithExistingId);

        // Act
        String viewName = userController.save(userWithExistingId, result, model, photo, flash);

        // Assert
        verify(userService, times(1)).saveInternalPhoto(photo, userWithExistingId);
        verify(userService, times(1)).save(userWithExistingId);
        verify(model).addAttribute("success", "Congratulation! You have an account");

        assertEquals("redirect:/userDetails/" + userWithExistingId.getId(), viewName);
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
