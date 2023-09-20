package com.example.demo.service;

import com.example.demo.entity.Problem;

import com.example.demo.mapper.Problemmapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Problemservice {
    @Autowired
    private Problemmapper problemmapper;

    public int save(Problem problem){
        if(problem.getId()==null){
            return   problemmapper.insert(problem);//新增
        } else{//更新
            return   problemmapper.update(problem);
        }
    }

}
