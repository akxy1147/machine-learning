package com.example.demo.service;


import com.example.demo.entity.Team;
import com.example.demo.mapper.Teammapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Teamservice {
    @Autowired
    private Teammapper teammapper;

    public int save(Team team){
        if(team.getId()==null){
            return   teammapper.insert(team);//新增
        } else{//更新
            return   teammapper.update(team);
        }
    }
}

