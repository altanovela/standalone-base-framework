package id.altanovela.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    
    @GetMapping("/e403")
    public String e403(Model model){
        return "/error/403";
    }
}
