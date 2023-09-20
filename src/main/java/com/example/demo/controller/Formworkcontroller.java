package com.example.demo.controller;

import com.example.demo.entity.Formwork;
import com.example.demo.mapper.Formworkmapper;
import com.example.demo.service.Formworkservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formwork")
public class Formworkcontroller {
    @Autowired
    private Formworkmapper formworkmapper;

    @Autowired
    private Formworkservice formworkservice;

    // 新增或者更新
    @PostMapping
    public Integer save(@RequestBody Formwork formwork) {
        return formworkservice.save(formwork);
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
        return formworkmapper.delete(id);
    }

    @GetMapping
    public List<Formwork> findall(){

        return formworkmapper.findall();
    }


    @GetMapping("/page")//分页查询
    public Map<String,Object> findpage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name
    ){
        pageNum=(pageNum-1)*pageSize;
        name="%"+name+"%";

        List<Formwork>data =formworkmapper.selectPage(pageNum,pageSize,name);
        Integer total=formworkmapper.selectTotal(name);
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",total);

        return res;

    }

}
