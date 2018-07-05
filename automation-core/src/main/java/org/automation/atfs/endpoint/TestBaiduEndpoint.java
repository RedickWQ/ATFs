package org.automation.atfs.endpoint;

import org.automation.atfs.annotation.EndPoint;
import org.automation.atfs.annotation.EndpointComponent;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.POST;

/**
 * Created by wangqiang on 2018/6/14
 */
@EndpointComponent
public interface TestBaiduEndpoint {
    @EndPoint(value = "https://www.baidu.com/")
    @POST
    ResponseEntity<String> getBaidu();
}
