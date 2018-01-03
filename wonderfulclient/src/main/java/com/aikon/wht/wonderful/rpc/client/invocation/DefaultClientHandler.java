package com.aikon.wht.wonderful.rpc.client.invocation;


import com.aikon.wht.wonderful.rpc.client.http.HttpMethod;
import com.aikon.wht.wonderful.rpc.client.pipe.ResponseParser;
import com.aikon.wht.wonderful.rpc.client.util.StringUtils;
import com.aikon.wht.wonderful.rpc.client.ann.BestWonderful;
import com.aikon.wht.wonderful.rpc.client.http.ContentType;
import com.aikon.wht.wonderful.rpc.client.http.HttpExecutor;
import com.aikon.wht.wonderful.rpc.client.http.PostExecutor;
import com.aikon.wht.wonderful.rpc.client.model.MethodWrapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author haitao.wang http://www.bestwonderful.com
 */
@Slf4j
public class DefaultClientHandler implements InvocationHandler, ClientHandler {


    private Configuration configuration;

    private HttpExecutor httpExecutor;


    public DefaultClientHandler(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (this.isRawMethod(method)) {
            return null;
        }
        BestWonderful ann = method.getAnnotation(BestWonderful.class);
        if (ann == null) {
            return null;
        }
        String path;
        path = ann.value();
        if (StringUtils.isBlank(path)) {
            path = method.getName();
        }
        path = this.formatPath(path);

        configuration.setPath(path);

        if (ann.json()) {
            configuration.setContentType(ContentType.JSON);
        }
        if (HttpMethod.POST.equals(ann.verb())) {
            httpExecutor = new PostExecutor(configuration);
        }
        MethodWrapper.MethodWrapperBuilder wrapperBuilder = MethodWrapper.builder();
        MethodWrapper methodWrapper = wrapperBuilder.args(args).argTypes(method.getParameterTypes()).build();

        String responseStr;
        try {
            responseStr = httpExecutor.execute(methodWrapper);
        } catch (Exception e) {
            log.error("Error Executing Http Request : {}",e);
            return null;
        }
        Class returnType = method.getReturnType();
        if (returnType.isAssignableFrom(CharSequence.class)) {
            return responseStr;
        }
        Object obj2Return = null;
        try {
            obj2Return = new ResponseParser().parse(responseStr,returnType);
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.info(responseStr);
            }
            log.error("Error Parsing Response From Json",e);
        }
        return obj2Return;
    }

    private String formatPath(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
            if (path.startsWith("/")) {
                this.formatPath(path);
            }
        }
        return "/" + path;
    }

    private boolean isRawMethod(Method method) {
        Method[] rawMethods = Object.class.getMethods();
        for (Method objMethod : rawMethods) {
            if (objMethod.toGenericString().equals(method.toGenericString())) {
                return true;
            }
        }
        return false;
    }
}
