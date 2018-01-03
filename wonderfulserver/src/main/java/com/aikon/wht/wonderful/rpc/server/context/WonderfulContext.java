package com.aikon.wht.wonderful.rpc.server.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author haitao.wang
 */
public class WonderfulContext {

    private WonderfulContext() {}

    static WonderfulContext wonderfulContext = new WonderfulContext();

    ApplicationContext applicationContext ;

    public static WonderfulContext instance() {
        return wonderfulContext;
    }

    public void init(String[] configLocations) {
        this.applicationContext = new ClassPathXmlApplicationContext(configLocations);
    }


}
