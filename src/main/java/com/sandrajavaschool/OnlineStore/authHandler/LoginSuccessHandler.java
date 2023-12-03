
package com.sandrajavaschool.OnlineStore.authHandler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;
import java.util.Locale;


@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //hay que hacerlo de esta forma porque para el login y logout
    //no se puede incluir el redirect atribbute, pero es
    //practicamente lo mismo

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        //para poder registrar un faslh map, un arreglo asociativo de java que pueda portar los flash

        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();

        FlashMap flashMap = new FlashMap();

        Locale locale = localeResolver.resolveLocale(request);
        String message = String.format(messageSource
                .getMessage("text.login.success", null, locale),
                authentication.getName());

        flashMap.put("success", message);

        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        //ESTO es para registrar nuestro logger con los usuarios que se
        // vayan registradno en userController

        if (authentication != null) {
            logger.info(message);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    //importante poder acceder al username para poder hacer consulta a la base de datos, pasarlo a la vista etc.
    //Suele ser util.




}


