package id.altanovela.web.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import id.altanovela.web.BaseController;

@Controller
public class HomeController extends BaseController {
    
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @GetMapping("/home")
    public String home(Model model){
        for (GrantedAuthority sga : getUserDetails().getAuthorities()) {
            if("ROLE_ADMIN".equals(sga.getAuthority())) {
                return "redirect:/account/index";
            } else if("ROLE_OPERATOR".equals(sga.getAuthority())) {
                return "redirect:/account/index";
            } else if("ROLE_BUSINESS_USER".equals(sga.getAuthority())) {
                return "redirect:/account/edit";
            }
        }
        
        // Default Homepage
        return "homepage";
    }
}
