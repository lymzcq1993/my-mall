package com.hujian.mall.search.service;

import cn.hutool.http.Header;
import com.hujian.mall.search.properties.ESProperties;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

/**
 * @author hujian
 */
@Service
public class ESService {
    @Autowired
    private RestHighLevelClient client;

    private static String auth;

    /**
     * @param esProperties
     */
    public ESService(ESProperties esProperties) {
        auth = Base64Utils.encodeToString((esProperties.getUser() + ":" + esProperties.getPwd()).getBytes());
        auth = "Basic " + auth;
    }

    public void testGet() throws Exception {
        // 文档查询
        GetRequest getRequest = new GetRequest("first-index", "gvarh3gBF9fSFsHNuO49");
        RequestOptions.Builder requestOptions = RequestOptions.DEFAULT.toBuilder();
        requestOptions.addHeader(Header.AUTHORIZATION.name(), auth);
        GetResponse getResponse = client.get(getRequest, requestOptions.build());
        if (getResponse.isExists()) {
            String sourceAsString = getResponse.getSourceAsString();
            System.out.println(sourceAsString);
        } else {
            System.out.println("no string!");
        }
    }
}
