package id.altanovela.conf.auth;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 5725067723883759820L;

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}