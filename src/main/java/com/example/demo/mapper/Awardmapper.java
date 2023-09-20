package com.example.demo.mapper;

import com.example.demo.entity.Award;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Awardmapper {
    @Select("Select * from sys_award")
    List<Award> findall();

    @Insert("INSERT into sys_award(name,national,province,school,`all`) VALUES(#{name},#{national},#{province},#{school},#{national}+#{province}+#{school})")
    int insert(Award award);

    @Update("update sys_award set name=#{name},national=#{national},province=#{province},school=#{school},`all`=#{national}+#{province}+#{school} where id=#{id}")
    int update(Award award);

    @Delete("delete from sys_award where id=#{id}")
    Integer delete(@Param("id") String id);

    @Select("Select * from sys_award where name like #{name}  limit #{pageNum}, #{pageSize}")
    List<Award> selectPage(Integer pageNum, Integer pageSize, String name);

    @Select("Select count(*) from sys_award where name like #{name}")
    Integer selectTotal(String name);
}
