package com.example.demo.mapper;

import com.example.demo.entity.Formwork;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Formworkmapper {



    @Select("Select * from sys_formwork")
    List<Formwork> findall();

    @Insert("INSERT into sys_formwork(name,content) VALUES(#{name},#{content})")
    int insert(Formwork formwork);

    @Update("update sys_formwork set name=#{name},content=#{content} where id=#{id}")
    int update(Formwork formwork);

    @Delete("delete from sys_formwork where id=#{id}")
    Integer delete(@Param("id") String id);

    @Select("Select * from sys_formwork where name like #{name}  limit #{pageNum}, #{pageSize}")
    List<Formwork> selectPage(Integer pageNum, Integer pageSize, String name);

    @Select("Select count(*) from sys_formwork where name like #{name}")
    Integer selectTotal(String name);
}


