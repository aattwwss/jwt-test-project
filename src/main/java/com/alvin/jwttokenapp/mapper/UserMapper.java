package com.alvin.jwttokenapp.mapper;

import com.alvin.jwttokenapp.model.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Select("SELECT * FROM app_user WHERE USERNAME = #{username}")
    UserEntity findUserByUsername(@Param("username") String username);

    @Insert("insert into app_user (username, password) values(#{username}, #{password});")
    void addUser(UserEntity entity);
}



