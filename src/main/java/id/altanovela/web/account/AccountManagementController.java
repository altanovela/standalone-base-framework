package id.altanovela.web.account;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import id.altanovela.constant.UserStatus;
import id.altanovela.dao.entities.User;
import id.altanovela.services.RoleService;
import id.altanovela.services.UploadFileService;
import id.altanovela.services.UserService;
import id.altanovela.util.EncryptUtil;
import id.altanovela.util.SecurityUtil;
import id.altanovela.util.TextUtil;
import id.altanovela.vo.AccountVo;
import id.altanovela.vo.AppUserDetails;
import id.altanovela.vo.Pagination;
import id.altanovela.web.BaseController;

/**
 * AccountManagementController used
 * 
 */
@Controller
@RequestMapping("account")
public class AccountManagementController extends BaseController {

    Logger logger = LoggerFactory.getLogger(AccountManagementController.class);
    
    @Autowired
    RoleService roleService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    UploadFileService uploadFileService;
    
    @GetMapping("/index")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String index(Model model){
        // Get All Available Role
        model.addAttribute("roleList", roleService.getRoleList());
        
        return "account/indexpage";
    }
    
    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String add(Model model){
        // Instantiate Wrapper
        model.addAttribute("account", new AccountVo());
        // Get All Available Role
        model.addAttribute("roleList", roleService.getRoleListNotSuperUser());
        
        return "account/addpage";
    }
    
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView save(Model model, 
            @ModelAttribute("account") AccountVo account, 
            @RequestParam(value = "file") MultipartFile image){
        
        if(TextUtil.isContainHtml(account.getUsername())){
            return new ModelAndView("redirect:/account/add?error");
        }
        if(uploadFileService.isfile(image)) {
            // Validate Image
            if(uploadFileService.isEligibleImage(image) == false){
                return new ModelAndView("redirect:/account/add?errimage2"); 
            }
        }
        
        // Upload Profile Image
        String filePath = uploadFileService.uploadImageFile(image);
        
        // Prepare User
        User user = new User();
        user.setEmail(EncryptUtil.encrypt(account.getEmail()));
        user.setUsername(account.getUsername());
        user.setPassword(SecurityUtil.encrypt(account.getPassword1()));
        user.setRoles(Arrays.asList(roleService.getRoleById(account.getRoleId())));
        user.setImage(filePath);
        user.setStatus(UserStatus.ACTIVE.toString());
        
        // Persist
        if(userService.save(user)>0) {
            return new ModelAndView("redirect:/account/add?ok");
        } else {
            return new ModelAndView("redirect:/account/add?error");
        }
    }
    
    @GetMapping("/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'BUSINESS_USER')")
    public String edit(Model model){
        // Instantiate Wrapper
        AccountVo account = new AccountVo(userService.getUser(getUserDetails().getId()));
        account.setEmail(EncryptUtil.decrypt(account.getEmail()));
        model.addAttribute("account", account);
        
        return "account/editpage";
    }
    
    @PostMapping("/updatepass")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'BUSINESS_USER')")
    public ModelAndView updatepass(Model model, @ModelAttribute("account") AccountVo account){
        // Prepare User
        AppUserDetails user = getUserDetails();
        User u = userService.getUser(user.getId());
        
        // Validate Password
        if(SecurityUtil.same(account.getPassword0(), u.getPassword())){
            if(SecurityUtil.same(account.getPassword1(), u.getPassword())) {
                return new ModelAndView("redirect:/account/edit?errpass2"); 
            }
        } else {
            return new ModelAndView("redirect:/account/edit?errpass1"); 
        }
        
        if(userService.updatePasswordUser(user.getId(), SecurityUtil.encrypt(account.getPassword1())) < 0) {
            return new ModelAndView("redirect:/account/edit?errpass3"); 
        }
        
        return new ModelAndView("redirect:/account/edit?okpass"); 
    }
    
    @PostMapping("/updateimage")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'BUSINESS_USER')")
    public ModelAndView updateimage(Model model, @RequestParam(value = "file") MultipartFile image){
        
        if(uploadFileService.isfile(image)) {
            // Validate Image
            if(uploadFileService.isEligibleImage(image) == false){
                return new ModelAndView("redirect:/account/edit?errimage2"); 
            }
        } else {
            return new ModelAndView("redirect:/account/edit?errimage2");
        }
                
        AppUserDetails user = getUserDetails();
        String filePath = uploadFileService.uploadImageFile(image);
        
        if(StringUtils.isBlank(filePath) || userService.updateImageUser(user.getId(), filePath) < 0) {
            return new ModelAndView("redirect:/account/edit?errimage");
        }
        
        // Reload Member Details on Success
        reloadUserDetails();
        return new ModelAndView("redirect:/account/edit?okimage");
    }
    
    @GetMapping("/emailya")
    @ResponseBody
    public String isEmailUnique(String e) {
        return userService.isEmailUnique(e);
    }
    
    @GetMapping("/userya")
    @ResponseBody
    public String isUserUnique(String u) {
        return userService.isUsernameUnique(u);
    }
    
    @GetMapping("/getuser")
    @ResponseBody
    public Pagination<AccountVo> getuser(String username, String email, Integer roleId, Integer start, Integer length, Integer draw) {
        return userService.getAllUser(username, email, roleId, PageRequest.of(start/length, length, Sort.by("id").descending()), draw);
    }
    
    @GetMapping("/inactivate")
    @ResponseBody
    public Integer inactivate(String user) {
        return userService.inactivateUser(user);
    }
}
