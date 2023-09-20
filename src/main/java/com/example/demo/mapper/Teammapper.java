package com.example.demo.mapper;


import com.example.demo.entity.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Teammapper {
    @Select("Select * from sys_team")
    List<Team> findall();

    @Select("Select name from sys_user")
    List<String> findstu();

    @Select("Select name from sys_teacher")
    List<String> findteacher();


    @Insert("INSERT into sys_team(teamname,stu1,stu2,stu3,coach) VALUES(#{teamname},#{stu1},#{stu2},#{stu3},#{coach})")
    int insert(Team team);

    @Update("update sys_team set teamname=#{teamname},stu1=#{stu1},stu2=#{stu2},stu3=#{stu3},coach=#{coach}  where id=#{id}")
    int update(Team team);

    @Delete("delete from sys_team where id=#{id}")
    Integer delete(@Param("id") String id);

    @Select("Select * from sys_team where teamname like #{teamname}  limit #{pageNum}, #{pageSize}")
    List<Team> selectPage(Integer pageNum, Integer pageSize, String teamname);

    @Select("Select count(*) from sys_team where teamname like #{teamname}")
    Integer selectTotal(String teamname);
}
