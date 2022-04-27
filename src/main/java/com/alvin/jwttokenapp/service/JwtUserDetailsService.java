package com.alvin.jwttokenapp.service;

import com.alvin.jwttokenapp.exception.UserAlreadyExistAuthenticationException;
import com.alvin.jwttokenapp.dao.UserDAO;
import com.alvin.jwttokenapp.model.dto.RegisterUserRequest;
import com.alvin.jwttokenapp.model.entity.Role;
import com.alvin.jwttokenapp.model.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {
    
    private final PasswordEncoder bcryptEncoder;

    private final UserDAO userDAO;

    public JwtUserDetailsService(PasswordEncoder bcryptEncoder, UserDAO userDAO) {
        this.bcryptEncoder = bcryptEncoder;
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userDAO.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        user.setRoles(userDAO.findRolesByUserId(user.getId()));
        Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        user.setAuthorities(userDAO.findAuthoritiesByRoleIds(roleIds));
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public void save(RegisterUserRequest user) throws Exception {
        AppUser dbUser = userDAO.findUserByUsername(user.getUsername());
        if (dbUser != null) {
            throw new UserAlreadyExistAuthenticationException("Username already taken: " + user.getUsername());
        }
        Role defaultRole = userDAO.findRole("USER");
        if (defaultRole == null) {
            log.error("Cannot find default role USER");
            throw new Exception("Error creating user.");
        }

        AppUser newUser = new AppUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        userDAO.addUser(newUser);
        userDAO.addUserRole(newUser.getId(), new HashSet<>(List.of(defaultRole.getId())));

    }
}