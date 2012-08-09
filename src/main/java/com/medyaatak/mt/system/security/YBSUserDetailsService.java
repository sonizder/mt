package com.medyaatak.mt.system.security;

import com.medyaatak.mt.system.user.User;
import com.medyaatak.mt.system.user.UserDao;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ginnun
 * Date: 18.09.2011
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
@Service
public class YBSUserDetailsService implements UserDetailsService {
    @Inject
    UserDao userDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        String password = null;
        User user;
        try {
            user = userDao.findByName(username);
            password = user.getPassword();

        } catch (NoResultException e) {           // tabloda hiç kullanıcı yok.
            password = username;
            user = new User();
            user.setName(username);
            user.setPassword(username);
            user.setFirstName(username);
            user.setLastName(username);
            userDao.save(user);
        }
        RequestContextHolder.currentRequestAttributes().setAttribute("user", user, RequestAttributes.SCOPE_SESSION);
        /** @TODO deprecated dan kurtar. **/
        org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(username, password, true, true, true, true, getAuthorities(true));
        return springUser;
    }

    private GrantedAuthority[] getAuthorities(boolean isAdmin) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
        authList.add(new GrantedAuthorityImpl("ROLE_USER"));
        if (isAdmin) {
            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }
        return authList.toArray(new GrantedAuthority[]{});
    }
}
