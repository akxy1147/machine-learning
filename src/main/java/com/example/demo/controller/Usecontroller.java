package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.demo.controller.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.Usermapper;
import com.example.demo.service.Userservice;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")

public class Usecontroller {
    @Autowired
    private Usermapper usermapper;

    @Autowired
    private Userservice userservice;

    // 新增或者更新
    @PostMapping
   public Integer save(@RequestBody User user) {
        return userservice.save(user);
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

    @PostMapping("/login")
    public Boolean login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return false;
        }

        return userservice.login(userDTO);
    }

    @DeleteMapping("/{id}")
    public  Integer delete(@PathVariable String id){
        //删除
        return usermapper.delete(id);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public  static final String FILES_KEY="FINDALL";

    @GetMapping
    public List<User> findall(){
        //1.从缓存获取数据
        String json =stringRedisTemplate.opsForValue().get(FILES_KEY);
        List<User> cache;
        if(StrUtil.isBlank(json)){
            cache=usermapper.findall();//从数据库取出数据
            //缓存到redis
            stringRedisTemplate.opsForValue().set(FILES_KEY,JSONUtil.toJsonStr(cache));
        }
        else {
            //从redis缓存中获取数据
            cache=JSONUtil.toBean(json, new TypeReference<List<User>>() {
            },true);

        }

        return cache;
    }


    @GetMapping("/page")//分页查询
    public  Map<String,Object> findpage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String username,
                                        @RequestParam String grade){
        pageNum=(pageNum-1)*pageSize;
        username="%"+username+"%";
        grade="%"+grade+"%";
        List<User>data =usermapper.selectPage(pageNum,pageSize,username,grade);
       Integer total=usermapper.selectTotal(username,grade);
       Map<String,Object> res=new HashMap<>();
       res.put("data",data);
       res.put("total",total);

        return res;

    }



    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = findall();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("username", "学号");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("grade", "年级");
        writer.addHeaderAlias("major", "专业");
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("qq", "QQ号码");
        writer.addHeaderAlias("email", "邮箱");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
//    @PostMapping("/import")
//    public void imp(MultipartFile file) throws Exception {
//        InputStream inputStream = file.getInputStream();
//        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
////        List<User> list = reader.readAll(User.class);
//
//        // 方式2：忽略表头的中文，直接读取表的内容
//        List<List<Object>> list = reader.read(1);
//        List<User> users = CollUtil.newArrayList();
//        for (List<Object> row : list) {
//            User user = new User();
//            user.setUsername(row.get(1).toString());
//            user.setPassword(row.get(2).toString());
//            user.setGrade(row.get(3).toString());
//            user.setMajor(row.get(4).toString());
//            user.setName(row.get(5).toString());
//            user.setPhone(row.get(6).toString());
//            user.setQq(row.get(7).toString());
//            user.setEmail(row.get(8).toString());
//            users.add(user);
//        }
//
//        userservice.save(users);
//
//    }

}
