package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private String username;
    private String password;
    private String name;
    private Integer id;
    private String phone;
    private String email;
    private String qq;
    private String grade;
    private String major;


}
