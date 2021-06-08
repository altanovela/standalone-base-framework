package id.altanovela.vo;

import id.altanovela.dao.entities.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountVo {

    private Integer id;
    
    private String username;
    
    private String password0;
    
    private String password1;
    
    private String password2;
    
    private String email;
    
    private String image;
    
    private Integer roleId;

    private String roleLabel;
    
    private String status;
    
    public AccountVo() {
        // do nothing
    }
    
    public AccountVo(Integer id, String username, String email, String image, Integer roleId, String roleLabel, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.image = image;
        this.roleId = roleId;
        this.roleLabel = roleLabel;
        this.status = status;
    }
    
    public AccountVo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail(); 
        this.image = user.getImage();
    }
}
