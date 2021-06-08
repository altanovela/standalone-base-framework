package id.altanovela.dao.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import id.altanovela.dao.entities.User;
import id.altanovela.vo.AccountVo;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    User findByEmail(String email);
    
    User findByUsername(String username);
    
    User findByEmailOrUsername(String email, String username);
    
    @Query("SELECT new id.altanovela.vo.AccountVo(u.id, u.username, u.email, u.image, r.id, r.namelabel, u.status) FROM User AS u LEFT JOIN u.roles AS r WHERE (?1 is null or u.username like ?1) and (?2 is null or u.email = ?2) and (?3 is null or r.id = ?3)")
    List<AccountVo> findByParam(String username, String email, Integer id, Pageable pageable);
    
    @Query("SELECT count(u.id) FROM User AS u LEFT JOIN u.roles AS r WHERE (?1 is null or u.username like ?1) and (?2 is null or u.email = ?2) and (?3 is null or r.id = ?3)")
    Integer findByParamCount(String username, String email, Integer id);
    
    @Query("SELECT count(u.id) FROM User AS u LEFT JOIN u.roles AS r")
    Integer findByParamCountAll();
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.status = 'NON_ACTIVE' WHERE u.username = ?1")
    void inactivateUser(String username);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = ?2 WHERE u.id = ?1")
    void updatePasswordUser(Integer id, String password);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.image = ?2 WHERE u.id = ?1")
    void updateImageUser(Integer id, String imagepath);
}
