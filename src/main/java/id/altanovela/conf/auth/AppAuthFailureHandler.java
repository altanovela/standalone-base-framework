package id.altanovela.conf.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AppAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        if(exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            response.sendRedirect("/login?errornotfound");
        } else {
            if(null != exception.getCause()) {
                Class exclass = exception.getCause().getClass();
                if(exclass.isAssignableFrom(UserNotFoundException.class)) {
                    response.sendRedirect("/login?errornotfound");
                } else if(exclass.isAssignableFrom(UserNotActiveException.class)) {
                    response.sendRedirect("/login?errornotactive");
                }    
            }
        }    
    }
}
