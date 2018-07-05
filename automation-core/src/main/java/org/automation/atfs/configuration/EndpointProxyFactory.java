package org.automation.atfs.configuration;


import org.automation.atfs.handler.RestEndpointInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * Created by wangqiang on 2018/6/13
 */
public class EndpointProxyFactory {
    public EndpointProxyFactory(){
        System.out.println("===============EndpointProxyFactory===================");
    }


    public  <T> T getProxyObject(ClassLoader classLoader, Class<T> clazz){

        System.out.println("===============getProxyObject===================");

        return (T) Proxy.newProxyInstance(classLoader,new Class[]{clazz}, new RestEndpointInvocationHandler(clazz));
    }
}