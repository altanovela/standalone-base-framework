package id.altanovela.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }
    
    /**
     * TODO : 
     * Need a better way, 
     * maybe using filter, or maybe an Intercept
     *  
     * @param model
     * @param principal
     * @return
     */
    @GetMapping("/login")
    public String login(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return "redirect:/home";
        }
        return "loginpage";
    }
}
