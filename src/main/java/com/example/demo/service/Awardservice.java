package com.example.demo.service;

import com.example.demo.entity.Award;
import com.example.demo.mapper.Awardmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Awardservice {
    @Autowired
    private Awardmapper awardmapper;

    public int save(Award award){
        if(award.getId()==null){
            return   awardmapper.insert(award);//新增
        } else{//更新
            return   awardmapper.update(award);
        }
    }
}
