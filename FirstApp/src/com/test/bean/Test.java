package com.test.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/10/17 0017.
 */
public class Test {
    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");//��ȡbean.xml�е�����
        Person p = ctx.getBean("person",Person.class);//����bean�����ö���
        p.info();
    }


}
