package com.gizwits.annotation;

/**
 * Created by feel on 16/9/1.
 */
@Bean(value = "prcess")
public class Process {


    @Color
    public void process(String name,int  age) {

        System.out.println("name-->" + name);
    }
}
