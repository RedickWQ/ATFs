package org.automation.atfs.utils;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liuyikai
 * @date 2017/9/4
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return APPLICATION_CONTEXT.getBean(beanClass);
    }

    public static <T> T getBean(Class<T> clazz, Object... args) {
        return APPLICATION_CONTEXT.getBean(clazz, args);
    }

    public static  <T> T getBean(String name, Object... args){
        return (T) APPLICATION_CONTEXT.getBean(name, args);
    }

    public static  <T> T getBean(String beanName){
        return (T) APPLICATION_CONTEXT.getBean(beanName);
    }

    public static Map<String, Object> getBeansByAnnotation(Class annotationType){
        return APPLICATION_CONTEXT.getBeansWithAnnotation(annotationType);
    }


    public static String [] getBeanDefinitionNamesByAnnotation(Class annotationType) {
        return APPLICATION_CONTEXT.getBeanNamesForAnnotation(annotationType);
    }

    public static Class getBeanClassByName(String beanName) throws ClassNotFoundException {
        BeanDefinition beanDefinition = ((ConfigurableApplicationContext)APPLICATION_CONTEXT).getBeanFactory().getBeanDefinition(beanName);
        String className = beanDefinition.getBeanClassName();
        return Class.forName(className);
    }

    public static List<Class> getBeanClassListByAnnotation(Class annotationType) throws ClassNotFoundException {
        List<Class> beanClassList = new ArrayList<>();
        String [] beanNameList = getBeanDefinitionNamesByAnnotation(annotationType);
        for (String beanName: beanNameList){
            Class clazz = getBeanClassByName(beanName);
            beanClassList.add(clazz);
        }

        return beanClassList;
    }



    public static Method getMethodByAnnotation(Object bean, Class annotationClass){
        Class clazz;
        if (AopUtils.isAopProxy(bean)){
            clazz = AopUtils.getTargetClass(bean);
        } else  {
            clazz = bean.getClass();
        }

        Method[] methods = clazz.getMethods();

        for (Method method: methods) {

            if (method.getAnnotation(annotationClass) != null) {
                return method;
            }
        }

        return null;

    }



}
