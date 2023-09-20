package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Problem {
    private String name;
    private Integer id;
    private Integer dp;
    private Integer greed;
    private Integer search;
    private Integer graph;
    private Integer math;
    private Integer simulate;
    private Integer all;
}
