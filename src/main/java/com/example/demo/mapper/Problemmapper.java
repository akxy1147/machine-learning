package com.example.demo.mapper;

import com.example.demo.entity.Problem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Problemmapper {
    @Select("Select * from sys_problem")
    List<Problem> findall();

    @Insert("INSERT into sys_problem(name,dp,greed,search,graph,math,simulate,`all`) VALUES(#{name},#{dp},#{greed},#{search},#{graph},#{math},#{simulate},#{math}+#{dp}+#{greed}+#{graph}+#{search}+#{simulate})")
    int insert(Problem problem);

    @Update("update sys_problem set name=#{name},dp=#{dp},greed=#{greed},search=#{search},graph=#{graph},math=#{math},simulate=#{simulate},`all`=#{math}+#{dp}+#{greed}+#{graph}+#{search}+#{simulate} where id=#{id}")
    int update(Problem problem);

    @Delete("delete from sys_problem where id=#{id}")
    Integer delete(@Param("id") String id);

    @Select("Select * from sys_problem where name like #{name}  limit #{pageNum}, #{pageSize}")
    List<Problem> selectPage(Integer pageNum, Integer pageSize, String name);

    @Select("Select count(*) from sys_problem where name like #{name}")
    Integer selectTotal(String name);
}
