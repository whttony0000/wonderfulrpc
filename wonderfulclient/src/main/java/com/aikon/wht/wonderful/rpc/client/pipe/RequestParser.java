package com.aikon.wht.wonderful.rpc.client.pipe;

import jodd.json.BeanSerializer;
import jodd.json.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haitao.wang
 */
public class RequestParser implements Parser {

    public Map<String, String> parse2Map(Object object) {
        final Map<String, String> target =new HashMap<String,String>();

        BeanSerializer beanSerializer = new BeanSerializer(
                new JsonSerializer().createJsonContext(null), object) {
            @SuppressWarnings("rawtypes")
            @Override
            protected void onSerializableProperty(String propertyName, Class propertyType,
                                                  Object value) {
                if (value == null) {
                    return;
                }
                target.put(propertyName, value.toString());
            }
        };

        beanSerializer.serialize();
        return target;
    }


    public String parse2Json(Object object) {
        return new JsonSerializer().deep(true).serialize(object);
    }


}
