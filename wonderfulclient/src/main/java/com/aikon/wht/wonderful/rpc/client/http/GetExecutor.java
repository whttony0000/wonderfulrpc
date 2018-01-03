package com.aikon.wht.wonderful.rpc.client.http;

import com.aikon.wht.wonderful.rpc.client.invocation.Configuration;
import com.aikon.wht.wonderful.rpc.client.model.MethodWrapper;
import org.apache.http.client.methods.HttpGet;

import java.io.UnsupportedEncodingException;

/**
 * @author haitao.wang
 */
public class GetExecutor extends AbstractHttpExecutor {
    public GetExecutor(Configuration configuration) {
        super(configuration);
    }

    @Override
    public HttpGet generateHttpRequest(MethodWrapper params) throws UnsupportedEncodingException {
        return null;
    }
}
