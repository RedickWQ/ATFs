package org.automation.atfs.handler;

import com.alibaba.fastjson.JSONObject;
import org.automation.atfs.annotation.*;
import org.automation.atfs.utils.SpringContextUtil;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.aspectj.bridge.Version.text;

/**
 * Created by wangqiang on 2018/6/13
 */
public class RestEndpointInvocationHandler implements InvocationHandler {
    private Class delegatedClass;
    public RestEndpointInvocationHandler(Class delegatedClass){
        this.delegatedClass = delegatedClass;
    }




    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        EndPoint endPoint = method.getAnnotation(EndPoint.class);
        if (endPoint == null) {
            //todo {do something}
            return null;
        }
        //==============获取和组装body==============
        //获取方法参数中的annotation，获取之后全部遍历，取不同的annotation进行判断、处理
        Type entityType = null;
        Object body = null;
        Map<String, Object> pathParamMap = new HashMap<>();
        Map<String, Object> queryParamMap = new HashMap<>();

        Annotation[][] paramAnns = method.getParameterAnnotations();
        for (int i = 0 ; i < paramAnns.length ; i ++) {
            Annotation[] anns = paramAnns[i];
            Map<Class,Annotation> annotationMap = new HashMap<>();
            for (int j = 0 ; j < anns.length ; j ++) {
                annotationMap.put(anns[j].annotationType(),anns[j]);
            }
            Object value = args[i];
            //如果所有参数都没有被注解注释，直接获取形参的类型，复制给
            if (annotationMap.isEmpty()) {
                //entityType : ResponseEntity<?>,或者其他类型，上下文暂时没有用到
                entityType = method.getGenericReturnType();
                body = value;
            }else {
                Annotation ann;
                //传入参数为null，且存在@DefaultValue，将default值赋值给value
                if (value == null && (ann = annotationMap.get(DefaultValue.class)) != null) {
                    value = ((DefaultValue)ann).value();
                    if ((ann = annotationMap.get(PathParam.class)) != null) {
                        pathParamMap.put(((PathParam)ann).value(),value);
                    }
                //参数上存在@PathParam注解，将实参存入对应map
                }else if ((ann = annotationMap.get(PathParam.class)) != null) {
                    pathParamMap.put(((PathParam)ann).value(),value);
                }else if ((ann = annotationMap.get(QueryParam.class)) != null){
                    queryParamMap.put(((QueryParam)ann).value(),value);
                }
            }

        }

        //==============拼接URL==============
        //拼接URL
        //todo host简单放在了EndPoint里，需要另外的实现
        StringBuffer URL = new StringBuffer(endPoint.value());

        //获取method上有没@Path注解，组装URL
        Path pathAnn = method.getAnnotation(Path.class);
        String pathStr = null;
        if (pathAnn != null) {
            if (URL.toString().endsWith("/") && pathAnn.value().startsWith("/")){
                pathStr = pathAnn.value().substring(1);
            }else{
                pathStr = pathAnn.value();
            }
            URL.append(pathStr);
        }
        URL.replace(0,URL.length(),packageURLByPathParam(URL.toString(),pathParamMap));
        URL.replace(0,URL.length(),packageURLByQueryParam(URL.toString(),pathParamMap));



        //==============组装headers==============
        Headers headersAnnotation = method.getAnnotation(Headers.class);
        MultiValueMap<String,String> headersMap = new LinkedMultiValueMap<>();
        if (headersAnnotation != null) {
            Header[] headers = headersAnnotation.value();
            for (int i = 0 ; i < headers.length ; i ++) {
                headersMap.add(headers[i].headerName(),headers[i].headerValue());
            }
        }


        //==============组装cookies==============




        //==============装配httpEntity==============
        //设置httpEntity，这里包含body和header
        HttpEntity httpEntity = new HttpEntity(body,headersMap);
        //获取request的方法
        org.springframework.http.HttpMethod requestMethod = getHttpMethod(method);
        //从spring容器中获取restTemplate
        RestTemplate template = SpringContextUtil.getBean("restTemplate");
        //执行请求
        return template.exchange(URL.toString(), requestMethod,httpEntity, getResquestReturnType(method));
    }



    private Class getResquestReturnType(Method method){
        return (Class)((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments()[0];
    }

    /**
     * 将原始的url和path参数组合,使用path的值替换URL中的{...}内容
     * @param URL  原始的URL
     * @param pathParamMap  path参数map
     * @return 组装完成的URL
     */
    private String packageURLByPathParam(String URL,Map<String,Object> pathParamMap){
        if (pathParamMap.isEmpty()) {
            return URL;
        }
        for (Map.Entry<String, Object> map: pathParamMap.entrySet()) {
            String key = map.getKey();
            Object value = map.getValue();
            String name = "{"+key+"}";
            URL = URL.replace(name,value.toString());
        }
        return URL;
    }

    private String packageURLByQueryParam(String URL,Map<String,Object> queryParamMap){
        if (queryParamMap.isEmpty()) {
            return URL;
        }
        StringBuffer URLBuffer = new StringBuffer(URL);
        URLBuffer.append("?");
        for (Map.Entry<String, Object> map: queryParamMap.entrySet()) {
            String key = map.getKey();
            Object value = map.getValue();
            URLBuffer.append("&"+key+"="+value);
        }
        return URLBuffer.toString();
    }



    //Endpoint接口中用的是javax.ws.rs包下的HttpMethod的注解，这里需要将这种类型转化为org.springframework.http下的method
    private org.springframework.http.HttpMethod getHttpMethod(Method method){
        String httpMethod = HttpMethod.GET;
        Annotation[] anns = method.getAnnotations();
        for (int i = 0 ; i < anns.length ; i ++) {
            HttpMethod a = anns[i].annotationType().getAnnotation(HttpMethod.class);
            if (a != null) {
                httpMethod = a.value();
                break;
            }
        }

        org.springframework.http.HttpMethod requestMethod = org.springframework.http.HttpMethod.resolve(httpMethod);
        return requestMethod;

    }
}
