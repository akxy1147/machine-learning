package com.example.demo.controller;

import com.example.demo.entity.Teacher;
import com.example.demo.mapper.Teachermapper;

import com.example.demo.service.Teacherservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class Teachercontroller {
    @Autowired
    private Teachermapper teachermapper;

    @Autowired
    private Teacherservice teacherservice;

    // 新增或者更新
    @PostMapping
    public Integer save(@RequestBody Teacher teacher) {
        return teacherservice.save(teacher);
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
        return teachermapper.delete(id);
    }

    @GetMapping
    public List<Teacher> findall(){

        return teachermapper.findall();
    }


    @GetMapping("/page")//分页查询
    public Map<String,Object> findpage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name
    ){
        pageNum=(pageNum-1)*pageSize;
        name="%"+name+"%";

        List<Teacher>data =teachermapper.selectPage(pageNum,pageSize,name);
        Integer total=teachermapper.selectTotal(name);
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",total);

        return res;

    }

}

