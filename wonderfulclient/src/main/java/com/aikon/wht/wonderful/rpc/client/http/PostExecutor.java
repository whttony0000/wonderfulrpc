package com.aikon.wht.wonderful.rpc.client.http;

import com.aikon.wht.wonderful.rpc.client.invocation.Configuration;
import com.aikon.wht.wonderful.rpc.client.model.MethodWrapper;
import com.aikon.wht.wonderful.rpc.client.pipe.RequestParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haitao.wang
 */
@Slf4j
public class PostExecutor extends AbstractHttpExecutor {

    public PostExecutor(Configuration configuration) {
        super(configuration);
    }

    public HttpPost generateHttpRequest(MethodWrapper methodWrapper) throws UnsupportedEncodingException {
        RequestParser requestParser = new RequestParser();
        HttpPost httpPost = new HttpPost(configuration.getPath());

        if (ContentType.JSON.equals(configuration.getContentType())) {
            String json = methodWrapper.getArgs() == null ? "" : requestParser.parse2Json(methodWrapper.getArgs()[0]);
            StringEntity stringEntity = new StringEntity(json);
            httpPost.addHeader("content-type",ContentType.JSON);
            httpPost.setEntity(stringEntity);
            return httpPost;
        }
        Map<String, String> params = methodWrapper.getArgs() == null ? new HashMap<String, String>() : requestParser.parse2Map(methodWrapper.getArgs()[0]);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, configuration.getCharset()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairs));
        return httpPost;
    }





}
