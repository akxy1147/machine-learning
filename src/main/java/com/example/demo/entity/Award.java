package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Award {
    private String name;
    private Integer id;
    private Integer national;
    private Integer province;
    private Integer school;
    private Integer all;
}
