package com.sandrajavaschool.OnlineStore.authHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;
import java.util.Locale;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Locale locale = localeResolver.resolveLocale(request);
        String message = String.format(messageSource.getMessage("text.logout.success", null, locale), authentication.getName());

        // Agregar mensaje de éxito al FlashMap
        FlashMap flashMap = new FlashMap();
        flashMap.put("success", message);

        // Guardar el FlashMap
        new SessionFlashMapManager().saveOutputFlashMap(flashMap, request, response);

        // Manejar cualquier lógica adicional si es necesario

        super.onLogoutSuccess(request, response, authentication);
    }
}
