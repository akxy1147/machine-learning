package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface Usermapper {
    @Select("Select * from sys_user")
    List<User>findall();

    @Select("SELECT * FROM sys_user WHERE id={id}")
    User getById(String id);



    @Insert("INSERT into sys_user(username,name,password,phone,email,qq,grade,major) VALUES(#{username},#{name},#{password},#{phone},#{email},#{qq},#{grade},#{major})")
    int insert(User user);

    @Update("update sys_user set username=#{username},name=#{name},password=#{password},phone=#{phone},email=#{email},qq=#{qq},grade=#{grade},major=#{major} where id=#{id}")
    int update(User user);

    @Delete("delete from sys_user where id=#{id}")
    Integer delete(@Param("id") String id);

    @Select("Select * from sys_user where username like #{username} and grade like #{grade} limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize,String username,String grade);
    @Select("Select count(*) from sys_user where username like #{username} and grade like #{grade}")
    Integer selectTotal(String username,String grade);
}
