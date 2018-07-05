package org.automation.atfs.helper;

import org.automation.atfs.annotation.AutomationComponent;
import org.automation.atfs.annotation.AutomationTestCase;
import org.automation.atfs.utils.SpringContextUtil;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqiang on 2018/7/4
 */
@Service
public class TestCaseHelper {
    public List<String> getTestCaseList(){
        List<String> testcaseList = new ArrayList<>();
        String[] testComponent = SpringContextUtil.getBeanDefinitionNamesByAnnotation(AutomationComponent.class);
        if (testComponent.length != 0 ) {
            for (int i  = 0 ; i < testComponent.length ; i ++) {
                testcaseList.addAll(getTestCaseEachComponent(testComponent[i]));
            }
        }else{
            System.out.println("==========fail to load test case===========");
        }

        return testcaseList;
    }

    public List<String> getTestCaseEachComponent(String testComponentName){
        List<String> testcaseList = new ArrayList<>();
        Class<?> clazz;
        try{
            clazz = SpringContextUtil.getBeanClassByName(testComponentName);
            Method[] methods = clazz.getMethods();
            if (methods.length == 0) {
                return testcaseList;
            }
            for (Method method : methods) {
                if (method.getAnnotation(AutomationTestCase.class) != null) {
                    testcaseList.add(method.getName());
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return testcaseList;
    }
}