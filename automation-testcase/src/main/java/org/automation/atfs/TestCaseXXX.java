package org.automation.atfs;

import org.automation.atfs.annotation.AutomationComponent;
import org.automation.atfs.annotation.AutomationTestCase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by wangqiang on 2018/7/4
 */
@AutomationComponent
public class TestCaseXXX {
    public TestCaseXXX() {
        System.out.println("this is org.automation.atfs.TestCase");
    }
    @AutomationTestCase
    public void testCaseComponent(){
        System.out.println("this is only for test");
    }

}