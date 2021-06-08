package id.altanovela.dao.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
@Table(name="t_roles")
public class Role{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable=false, unique=true)
    private String name;
    
    @Column(name="name_label", nullable=false, unique=true)
    private String namelabel;
    
    @JsonBackReference
    @ManyToMany(mappedBy="roles")
    private List<User> users;
}
