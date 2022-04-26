package com.alvin.jwttokenapp.service;

import com.alvin.jwttokenapp.exception.UserAlreadyExistAuthenticationException;
import com.alvin.jwttokenapp.mapper.UserMapper;
import com.alvin.jwttokenapp.model.dto.UserDTO;
import com.alvin.jwttokenapp.model.entity.Role;
import com.alvin.jwttokenapp.model.entity.UserEntity;
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

    private final UserMapper userMapper;

    public JwtUserDetailsService(PasswordEncoder bcryptEncoder, UserMapper userMapper) {
        this.bcryptEncoder = bcryptEncoder;
        this.userMapper = userMapper;
    }

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

    public void save(UserDTO user) throws Exception {
        UserEntity dbUser = userMapper.findUserByUsername(user.getUsername());
        if (dbUser != null) {
            throw new UserAlreadyExistAuthenticationException("Username already taken: " + user.getUsername());
        }
        Role defaultRole = userMapper.findRole("USER");
        if (defaultRole == null) {
            log.error("Cannot find default role USER");
            throw new Exception("Error creating user.");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        userMapper.addUser(newUser);
        userMapper.addUserRole(newUser.getId(), new HashSet<>(List.of(defaultRole.getId())));

    }
}