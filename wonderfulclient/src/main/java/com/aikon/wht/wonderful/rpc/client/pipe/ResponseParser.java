package com.aikon.wht.wonderful.rpc.client.pipe;

import jodd.json.JsonParser;

/**
 * @author haitao.wang
 */
public class ResponseParser implements Parser {

    public <T> T parse(String json,Class<?> returnType) {
        return (T) new JsonParser().parse(json, returnType);
    }
}
