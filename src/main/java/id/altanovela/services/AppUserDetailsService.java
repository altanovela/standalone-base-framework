package id.altanovela.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.altanovela.conf.auth.UserNotActiveException;
import id.altanovela.conf.auth.UserNotFoundException;
import id.altanovela.constant.UserStatus;
import id.altanovela.dao.entities.User;
import id.altanovela.dao.repo.UserRepository;
import id.altanovela.vo.AppUserDetails;

@Service
@Transactional
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(userName, userName);
        
        if(null == user) {
            throw new UserNotFoundException("Email/Username [" + userName + "] tidak ditemukan");
        } else {
            if(UserStatus.NON_ACTIVE.toString().equals(user.getStatus())) {
                throw new UserNotActiveException("Email/Username [" + userName + "Tidak Aktif");
            }
        }
        return new AppUserDetails(user, getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}
