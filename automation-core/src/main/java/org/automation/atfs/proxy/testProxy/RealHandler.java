package org.automation.atfs.proxy.testProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wangqiang on 2018/6/21
 */
public class RealHandler implements InvocationHandler{
    private Subject subject;
    public RealHandler(Subject subject){
        this.subject = subject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("begin");
        System.out.println("this is a handler");
        method.invoke(subject,args);
        System.out.println("game over");
        return null;
    }
}