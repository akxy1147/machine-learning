package com.example.demo.controller;
import com.example.demo.entity.Team;
import com.example.demo.mapper.Teammapper;
import com.example.demo.service.Teamservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/team")
public class Teamcontroller {
    @Autowired
    private Teammapper teammapper;

    @Autowired
    private Teamservice teamservice;

    // 新增或者更新
    @PostMapping
    public Integer save(@RequestBody Team team) {
        return teamservice.save(team);
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
        return teammapper.delete(id);
    }

    @GetMapping
    public List<Team> findall(){

        return teammapper.findall();
    }

    @GetMapping("/stu")
    public List<String> findstu(){

        return teammapper.findstu();
    }

    @GetMapping("/teacher")
    public List<String> findteacher(){

        return teammapper.findteacher();
    }

    @GetMapping("/page")//分页查询
    public Map<String,Object> findpage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String teamname
    ){
        pageNum=(pageNum-1)*pageSize;
        teamname="%"+teamname+"%";

        List<Team>data =teammapper.selectPage(pageNum,pageSize,teamname);
        Integer total=teammapper.selectTotal(teamname);
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",total);

        return res;

    }

}
