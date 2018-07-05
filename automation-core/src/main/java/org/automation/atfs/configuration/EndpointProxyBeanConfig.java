package org.automation.atfs.configuration;

import org.automation.atfs.annotation.EnableEndPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangqiang on 2018/6/13
 */
@Configuration
@EnableEndPoint({
        "org.automation.atfs"
})
public class EndpointProxyBeanConfig {
    @Bean(name = "EndpointProxyFactory" )
    public EndpointProxyFactory endpointProxyFactory(){
        return new EndpointProxyFactory();
    }
}