package com.taogen.easymall.admin;

import com.taogen.easymall.common.HelloTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    @GetMapping("/hello")
    public String hello()
    {
        return HelloTest.getHello("admin...");
    }
}
