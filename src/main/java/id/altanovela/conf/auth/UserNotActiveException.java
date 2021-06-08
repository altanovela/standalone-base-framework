package id.altanovela.conf.auth;

import org.springframework.security.core.AuthenticationException;

public class UserNotActiveException extends AuthenticationException {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = -3714236859679602385L;

    public UserNotActiveException(String msg) {
        super(msg);
    }

    public UserNotActiveException(String msg, Throwable t) {
        super(msg, t);
    }
}