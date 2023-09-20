package com.example.demo.service;

import com.example.demo.entity.Formwork;
import com.example.demo.mapper.Formworkmapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Formworkservice {
    @Autowired
    private Formworkmapper formworkmapper;

    public int save(Formwork formwork){
        if(formwork.getId()==null){
            return   formworkmapper.insert(formwork);//新增
        } else{//更新
            return   formworkmapper.update(formwork);
        }
    }

}
