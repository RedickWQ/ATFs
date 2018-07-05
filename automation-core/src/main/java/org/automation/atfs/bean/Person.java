package org.automation.atfs.bean;

import lombok.Data;

/**
 * Created by wangqiang on 2018/6/29
 */
@Data
public class Person {
    private String name;
    private int age;
    public Person(String name,int age){
        this.name = name;
        this.age= age;
    }

}