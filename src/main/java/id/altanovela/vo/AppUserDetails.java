package id.altanovela.vo;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import id.altanovela.dao.entities.User;

public class AppUserDetails extends org.springframework.security.core.userdetails.User {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = -7653977336811653591L;
    private Integer id;
    private String email;
    private String image;
    
    public AppUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        setId(user.getId());
        setEmail(user.getEmail());
        setImage(user.getImage());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
