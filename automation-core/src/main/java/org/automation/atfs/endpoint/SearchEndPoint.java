package org.automation.atfs.endpoint;

import com.alibaba.fastjson.JSONObject;
import org.automation.atfs.annotation.EndPoint;
import org.automation.atfs.annotation.EndpointComponent;
import org.automation.atfs.annotation.PathParam;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by wangqiang on 2018/6/25
 */
@EndpointComponent
public interface SearchEndPoint {
    @EndPoint(value = "http://s-search-search-api.patsnap.qa")
    @POST
    @Path(value = "/searchapi/2.0.0/patent/{search}")
    ResponseEntity<JSONObject> getSearchResult(@PathParam(value = "search") @DefaultValue(value = "search") String str, JSONObject object);
}
