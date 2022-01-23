package com.alvin.jwttokenapp.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

@Data
@EqualsAndHashCode
public class Authority implements GrantedAuthority {
    private long id;
    private String authority;
}
