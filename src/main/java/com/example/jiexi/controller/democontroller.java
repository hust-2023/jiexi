package com.example.jiexi.controller;

import com.example.jiexi.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class democontroller {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(String nic,String phone){
        return "hello"+nic+phone;
    }
    @RequestMapping(value = "/post",method = RequestMethod.POST)
        public String posthello(@RequestBody User user){
        System.out.println(user);
            return "possssat";
        }


}
