package org.automation.atfs.proxy.testProxy;

/**
 * Created by wangqiang on 2018/6/21
 */
public class RealSubject implements Subject {
    @Override
    public void say(String str) {
        System.out.println("hello! "+str);
    }

    @Override
    public void run() {
        System.out.println("i am running");
    }
}