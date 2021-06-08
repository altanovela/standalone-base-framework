package id.altanovela.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.altanovela.dao.entities.Role;
import id.altanovela.dao.repo.RoleRepository;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    
    /**
     * Find All Available Role
     * @return
     */
    public List<Role> getRoleList(){
        return roleRepository.findAll();
    }
    
    /**
     * Find All Available Role Not Super Admin
     * @return
     */
    public List<Role> getRoleListNotSuperUser(){
        List<Role> roles1 = roleRepository.findAll();
        List<Role> roles2 = new ArrayList<Role>();
        for (Role role : roles1) {
            if("ROLE_ADMIN".equals(role.getName())){
                continue;
            } else {
                roles2.add(role);
            }
        }
        return roles2;
    }
    
    /**
     * Find Role By Id, save type
     * @param id
     * @return
     */
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElse(new Role());
    }
}
