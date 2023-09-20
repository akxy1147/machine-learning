package com.example.demo.service;


import com.example.demo.entity.Teacher;
import com.example.demo.mapper.Teachermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Teacherservice {
    @Autowired
    private Teachermapper teachermapper;

    public int save(Teacher teacher){
        if(teacher.getId()==null){
            return   teachermapper.insert(teacher);//新增
        } else{//更新
            return   teachermapper.update(teacher);
        }
    }
}
