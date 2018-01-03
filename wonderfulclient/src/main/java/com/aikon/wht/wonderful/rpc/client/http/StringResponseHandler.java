package com.aikon.wht.wonderful.rpc.client.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author haitao.wang
 */
public class StringResponseHandler implements ResponseHandler<String> {
    @Override
    public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status >= 200 && status < 400) {
            HttpEntity entity = httpResponse.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
