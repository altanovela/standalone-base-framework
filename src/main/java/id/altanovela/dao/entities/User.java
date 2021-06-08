package id.altanovela.dao.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
@Table(name="t_users")
public class User {
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable=false, unique=true)
    private String username;
    
    @Column(nullable=false, unique=true)
    private String email;
    
    @Column(nullable=false)
    @Size(min=4)
    private String password;
    
    @JsonManagedReference
    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
          name="t_user_role",
          joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
          inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles;
    
    @Column(nullable=true)
    private String image; 
    
    @Column(nullable=false)
    private String status;
}
