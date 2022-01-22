package com.alvin.jwttokenapp.mapper;

import com.alvin.jwttokenapp.model.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Select("SELECT * FROM app_user WHERE username = #{username}")
    UserEntity findUserByUsername(@Param("username") String username);

    @Insert("INSERT INTO app_user (username, password) VALUES(#{username}, #{password});")
    void addUser(UserEntity entity);
}



