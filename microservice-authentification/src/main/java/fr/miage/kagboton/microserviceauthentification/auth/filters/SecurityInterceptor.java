package fr.miage.kagboton.microserviceauthentification.auth.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private static  final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    public static final String AUTHENTIFICATION_LOGIN_URI = "http://localhost:8888/authentification/login";
    public static final String TOKEN = "token";
    public static final String LOCATION = "location";



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenToCheck = request.getHeader(TOKEN);

        if(null == tokenToCheck || tokenToCheck.equals("")){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader(LOCATION, AUTHENTIFICATION_LOGIN_URI);
            return false;
        }
        String login = tokenToCheck;

        if(login != null){
            request.setAttribute("login", login);
            return true;
        }

        response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        response.setHeader(LOCATION, AUTHENTIFICATION_LOGIN_URI);
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
