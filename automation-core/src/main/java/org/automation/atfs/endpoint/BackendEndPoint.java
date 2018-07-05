package org.automation.atfs.endpoint;

import com.alibaba.fastjson.JSONObject;
import org.automation.atfs.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by wangqiang on 2018/6/25
 */
@EndpointComponent
public interface BackendEndPoint {
    @EndPoint(value = "http://s-insights-src.patsnap.qa")
    @POST
    @Headers({
            @Header(headerName = "Content-Type",headerValue = "application/json"),
            @Header(headerName = "USER_ID",headerValue = "dfe390bba2474de29acd0922ae0069a7"),
            @Header(headerName = "USER_ID",headerValue = "dfe390bba2474de29acd0922ae0069a7")
    })
    @Path(value = "/insights/{version}/report/company/geographic_distribution_map")
    ResponseEntity<JSONObject> getDistributionValues(@PathParam(value = "version") @DefaultValue(value = "1.6") String version,
                                                     JSONObject object);
}
