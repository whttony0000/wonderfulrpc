package com.aikon.wht.wonderful.rpc.client.http;

import com.aikon.wht.wonderful.rpc.client.model.MethodWrapper;

/**
 * @author haitao.wang
 */
public interface HttpExecutor {

    String execute(MethodWrapper wrapper) throws Exception;
}
