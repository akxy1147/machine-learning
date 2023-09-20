package com.example.demo.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.Usermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Userservice {

    @Autowired
    private Usermapper usermapper;

    public int save(User user){
           if(user.getId()==null){

           return   usermapper.insert(user);//新增

           } else{//更新
              return   usermapper.update(user);
          }
    }


    public User getById(String id){
      return usermapper.getById(id);
    }

    public Boolean login(UserDTO userDTO) {

        return false;
    }
}
