package id.altanovela.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import id.altanovela.dao.entities.User;
import id.altanovela.dao.repo.UserRepository;
import id.altanovela.util.EncryptUtil;
import id.altanovela.vo.AccountVo;
import id.altanovela.vo.Pagination;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    UserRepository userRepository;
    
    /**
     * Get User by Id, Save value
     * @param id
     * @return
     */
    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(new User());
    }
    
    /**
     * Save User, return it's Entity Id
     * @param user
     */
    public Integer save(User user) {
        User res = userRepository.save(user);
        logger.error("Here is the Id {}", res.getId());
        return res.getId();
    }
    
    /**
     * Is Email Unique
     * @param email
     * @return
     */
    public String isEmailUnique(String email) {
        User user = userRepository.findByEmail(email);
        if(null == user) {
            return "ya";
        } else {
            return "noya";
        }
    }
    
    /**
     * Is Username Unique
     * @param email
     * @return
     */
    public String isUsernameUnique(String username) {
        User user = userRepository.findByUsername(username);
        if(null == user) {
            return "ya";
        } else {
            return "noya";
        }
    }

    public Pagination<AccountVo> getAllUser(String username, String email, Integer roleId, Pageable page, Integer draw){
        if(StringUtils.isBlank(email)) {
            email = null;
        }
        List<AccountVo> list = userRepository.findByParam(like(username), email, roleId, page);
        for (AccountVo accountVo : list) {
            accountVo.setEmail(EncryptUtil.decrypt(accountVo.getEmail()));
        }
        return new Pagination<AccountVo>(draw, 
                userRepository.findByParamCountAll(), 
                userRepository.findByParamCount(like(username), like(email), roleId), 
                list
        );
    }
    
    public String like(String d) {
        if(StringUtils.isBlank(d)) {
            return null;
        }
        return "%" + d + "%";
    }
    
    public Integer inactivateUser(String user) {
        try {
            userRepository.inactivateUser(user);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
    
    public Integer updatePasswordUser(Integer id, String password) {
        try {
            userRepository.updatePasswordUser(id, password);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
    
    public Integer updateImageUser(Integer id, String imagepath) {
        try {
            userRepository.updateImageUser(id, imagepath);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
}
