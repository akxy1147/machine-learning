package com.example.demo.controller;
import com.example.demo.entity.Problem;
import com.example.demo.mapper.Problemmapper;
import com.example.demo.service.Problemservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/problem")
public class Problemcontroller {
    @Autowired
    private Problemmapper problemmapper;

    @Autowired
    private Problemservice problemservice;

    // 新增或者更新
    @PostMapping
    public Integer save(@RequestBody Problem problem) {
        return problemservice.save(problem);
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
        return problemmapper.delete(id);
    }

    @GetMapping
    public List<Problem> findall(){

        return problemmapper.findall();
    }


    @GetMapping("/page")//分页查询
    public Map<String,Object> findpage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name
                                     ){
        pageNum=(pageNum-1)*pageSize;
        name="%"+name+"%";

        List<Problem>data =problemmapper.selectPage(pageNum,pageSize,name);
        Integer total=problemmapper.selectTotal(name);
        Map<String,Object> res=new HashMap<>();
        res.put("data",data);
        res.put("total",total);

        return res;

    }


}
