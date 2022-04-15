package com.alvin.jwttokenapp.service;

import com.alvin.jwttokenapp.exception.UserAlreadyExistAuthenticationException;
import com.alvin.jwttokenapp.mapper.UserMapper;
import com.alvin.jwttokenapp.model.dto.UserDTO;
import com.alvin.jwttokenapp.model.entity.Role;
import com.alvin.jwttokenapp.model.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userMapper.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        user.setRoles(userMapper.findRolesByUserId(user.getId()));
        Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        user.setAuthorities(userMapper.findAuthoritiesByRoleIds(roleIds));
        log.info(user.toString());
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public void save(UserDTO user) {
        UserEntity dbUser = userMapper.findUserByUsername(user.getUsername());
        if (dbUser != null) {
            throw new UserAlreadyExistAuthenticationException("Username already taken: " + user.getUsername());
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        userMapper.addUser(newUser);

    }
}