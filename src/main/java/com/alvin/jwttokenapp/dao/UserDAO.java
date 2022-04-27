package com.alvin.jwttokenapp.dao;

import com.alvin.jwttokenapp.model.entity.Authority;
import com.alvin.jwttokenapp.model.entity.Role;
import com.alvin.jwttokenapp.model.entity.AppUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface UserDAO {


    @Select("select * from app_user where username = #{username};")
    AppUser findUserByUsername(@Param("username") String username);

    @Insert("INSERT INTO app_user (username, password) VALUES(#{username}, #{password});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUser(AppUser entity);

    @Insert("""
            <script>
            INSERT INTO app_user_role (
                user_id,
                role_id
            )
            VALUES
                <foreach item='roleId' collection='roleIds' open='' separator=',' close=''>
                    (
                        #{userId},
                        #{roleId}
                    )
                </foreach>
            </script>
            """)
    void addUserRole(@Param("userId") long userId, @Param("roleIds") Set<Long> roleIds);


    @Select("""
                select ar.id, role
                    from app_role as ar
                        inner join app_user_role aur on ar.id = aur.role_id
                        inner join app_user au on au.id = aur.user_id
                            where user_id = #{userId};
            """)
    Set<Role> findRolesByUserId(@Param("userId") long userId);

    @Select("select * from app_role where role = #{role};")
    Role findRole(@Param("role") String role);

    @Select(""" 
            <script>
                select *
                    from app_authority as aa
                        inner join app_role_authority ara on aa.id = ara.authority_id
                        inner join app_role ar on ar.id = ara.role_id
                            where role_id in
                            <foreach collection="roleIds" item="roleId" open="(" close=")" separator=",">
                                #{roleId}
                            </foreach>
            </script>
            """)
    Set<Authority> findAuthoritiesByRoleIds(@Param("roleIds") Set<Long> roleIds);
}



