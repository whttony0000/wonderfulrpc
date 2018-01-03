package com.aikon.wht.wonderful.rpc.client.spring.bean;

import com.aikon.wht.wonderful.rpc.client.factory.ClientProxyFactory;
import com.aikon.wht.wonderful.rpc.client.invocation.Configuration;
import com.aikon.wht.wonderful.rpc.client.invocation.DefaultClientHandler;

import java.lang.reflect.InvocationHandler;

/**
 * @author haitao.wang
 */
public class ClientBeanFactory {

    private ClientProxyFactory clientProxyFactory;

    public ClientBeanFactory(Configuration configuration) {
        this.generateClientProxyFactory(configuration);
    }

    public <T> T create(Class<T> clazz) {
        return clientProxyFactory.create(clazz);
    }

    private void generateClientProxyFactory(Configuration configuration) {
        InvocationHandler clientHandler = new DefaultClientHandler(configuration);
        ClientProxyFactory.Builder builder = new ClientProxyFactory.Builder();
        clientProxyFactory = builder.invocationHandler(clientHandler).build();
    }

}
