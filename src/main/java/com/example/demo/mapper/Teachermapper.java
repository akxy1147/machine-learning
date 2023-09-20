package com.example.demo.mapper;


import com.example.demo.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface Teachermapper {
    @Select("Select * from sys_teacher")
    List<Teacher> findall();

    @Insert("INSERT into sys_teacher(name,qq,phone,major) VALUES(#{name},#{qq},#{phone},#{major})")
    int insert(Teacher teacher);

    @Update("update sys_teacher set name=#{name},qq=#{qq},phone=#{phone},major=#{major} where id=#{id}")
    int update(Teacher teacher);

    @Delete("delete from sys_teacher where id=#{id}")
    Integer delete(@Param("id") String id);

    @Select("Select * from sys_teacher where name like #{name}  limit #{pageNum}, #{pageSize}")
    List<Teacher> selectPage(Integer pageNum, Integer pageSize, String name);

    @Select("Select count(*) from sys_teacher where name like #{name}")
    Integer selectTotal(String name);
}
