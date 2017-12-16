package com.test.bean;

/**
 * Created by Administrator on 2017/10/17 0017.
 */
public class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void info(){
        System.out.println("Ò»ÆğÀ´³ÔÂéÀ±ÌÌ£¡");
        System.out.println("name:"+getName()+" age:"+getAge());
    }
}
