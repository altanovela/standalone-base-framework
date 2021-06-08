package id.altanovela.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import id.altanovela.vo.AppUserDetails;

@Service
public class BaseController {

    @Autowired
    UserDetailsService customUserDetailsService;
    
    /**
     * Get User Details, and convert to CustomUserDetails
     * @return
     */
    public AppUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return (AppUserDetails) auth.getPrincipal();
        }
        return null;
    }
    
    /**
     * Reload User Details
     * @return
     */
    public void reloadUserDetails() {
        UserDetails user = customUserDetailsService.loadUserByUsername(getUserDetails().getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
