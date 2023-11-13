
package com.sandrajavaschool.OnlineStore.authHandler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;


@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //hay que hacerlo de esta forma porque para el login y logout
    //no se puede incluir el redirect atribbute, pero es
    //practicamente lo mismo
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        //para poder registrar un faslh map, un arreglo asociativo de java que pueda portar los flash

        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
        FlashMap flashMap = new FlashMap();

        flashMap.put("success", authentication.getName() + "You have logged in");
        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        //ESTO es para registrar nuestro logger con los usuarios que se
        // vayan registradno en userController
        if (authentication != null) {
            logger.info("The user has logged in, " + authentication.getName());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    //importante poder acceder al username para poder hacer consulta a la base de datos, pasarlo a la vista etc.
    //Suele ser util.
}


