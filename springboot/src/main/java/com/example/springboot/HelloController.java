package com.example.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Matthew
 * @date: 2019/10/27 20:57
 */
@RestController
public class HelloController {
    @RequestMapping("hello")
    public String helloSpringBoot(){
        System.out.println("springboot test");
        System.out.println("testing");
        return "hello SpringBoot";
    }
}
