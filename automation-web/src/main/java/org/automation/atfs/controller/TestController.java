package org.automation.atfs.controller;

import base.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqiang on 2018/7/4
 */
@RestController
public class TestController {
    @RequestMapping("/testThymeleaf")
    public ModelAndView testThymeleaf(){
        ModelAndView modelAndView = new ModelAndView();
        List<Person> people = new ArrayList<>();
        people.add(new Person("Dean",22));
        people.add(new Person("Paul",25));
        people.add(new Person("Tina",28));

        Person singlePerson = new Person("Dul",22);
        modelAndView.addObject("people",people);
        modelAndView.addObject("singlePerson",singlePerson);
        return modelAndView;
    }
}