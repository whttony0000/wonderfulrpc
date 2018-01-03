package com.aikon.wht.wonderful.rpc.client.factory;

import com.aikon.wht.wonderful.rpc.client.invocation.Configuration;
import com.aikon.wht.wonderful.rpc.client.invocation.DefaultClientHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author haitao.wang http://www.bestwonderful.com
 */
public class ClientProxyFactory {

    InvocationHandler rpcClientHandler;

    private ClientProxyFactory(Builder builder) {
        if (builder.invocationHandler != null) {
            this.rpcClientHandler = builder.invocationHandler;
            return;
        }
        this.rpcClientHandler = new DefaultClientHandler(new Configuration());
    }

    public <T> T create(Class<?> rpcClientClass) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{rpcClientClass}, rpcClientHandler);
    }

    public static class Builder{

        InvocationHandler invocationHandler;

        public Builder () {

        }

        public Builder invocationHandler(InvocationHandler invocationHandler ) {
            this.invocationHandler = invocationHandler;
            return this;
        }


        public ClientProxyFactory build() {
            return new ClientProxyFactory(this);
        }
    }
}
