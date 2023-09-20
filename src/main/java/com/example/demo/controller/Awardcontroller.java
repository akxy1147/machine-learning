package com.example.demo.controller;

import com.example.demo.entity.Award;

import com.example.demo.mapper.Awardmapper;
import com.example.demo.service.Awardservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/award")
public class Awardcontroller {
    @Autowired
    private Awardmapper awardmapper;

    @Autowired
    private Awardservice awardservice;

    // 新增或者更新
    @PostMapping
    public Integer save(@RequestBody Award award) {
        return awardservice.save(award);
    }

//    @PostMapping("/add")
//    public Integer insert(@RequestBody User user){
//        //新增
//        return usermapper.insert(user);
//    }
//
//   @PostMapping("/update")
//   public Integer update(@RequestBody User user){
//        //更新
//        return usermapper.update(user);
//    }

    @DeleteMapping("/{id}")
    public  Integer delete(@PathVariable String id){
        //删除
        return awardmapper.delete(id);
    }

    @GetMapping
    public List<Award> findall(){

        return awardmapper.findall();
    }


    @GetMapping("/page")//分页查询
    public Map<String,Object> findpage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name
    ){
        pageNum=(pageNum-1)*pageSize;
        name="%"+name+"%";

        List<Award>data =awardmapper.selectPage(pageNum,pageSize,name);
        Integer total=awardmapper.selectTotal(name);
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",total);

        return res;

    }

}
