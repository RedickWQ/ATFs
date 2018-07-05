package org.automation.atfs.controller;

import base.TestCaseInstance;
import org.automation.atfs.annotation.AutomationTestCase;
import org.automation.atfs.helper.TestCaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqiang on 2018/7/4
 */
@RestController
@RequestMapping(value = "/test")
public class MainPageController {
    @Autowired
    TestCaseHelper testCaseHelper;

    @RequestMapping(value = "/testCaseList")
    public ModelAndView testCaseList(ModelAndView modelAndView){
        List<String> caseNameList = testCaseHelper.getTestCaseList();
        List<TestCaseInstance> testCaseInstances = new ArrayList<>();
        for (String caseName:caseNameList) {
            TestCaseInstance testCaseInstance = new TestCaseInstance();
            testCaseInstance.setName(caseName);
            testCaseInstances.add(testCaseInstance);
        }
        modelAndView.setViewName("/testCaseList");
        modelAndView.addObject("testCaseInstances",testCaseInstances);
        return modelAndView;
    }

    @RequestMapping(value = "/detail")
    public ModelAndView goToDetail(ModelAndView modelAndView){


        return modelAndView;
    }
}