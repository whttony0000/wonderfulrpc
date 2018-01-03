package com.aikon.wht.wonderful.rpc.server.filter;


import com.aikon.wht.wonderful.rpc.server.context.WonderfulContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author haitao.wang
 */
public class WonderfulFilter implements Filter {

    private static final String APPLICATION_CONTEXT_XML_LOCATION = "applicationContextXmlLocation";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String xmlLocation = filterConfig.getInitParameter(APPLICATION_CONTEXT_XML_LOCATION);
        this.initContext(xmlLocation);
//        this.load
    }

    private void initContext(String xmlLocation) {
        String[] configLocations;
        if (StringUtils.isBlank(xmlLocation)) {
            configLocations = new String[]{"classpath*:applicationContext.xml"};
        }
        configLocations = xmlLocation.split("[,]");
        Arrays.stream(configLocations).forEach((configLocation)-> configLocation = configLocation.trim());
        WonderfulContext.instance().init(configLocations);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }

    public static void main(String[] args) {
    }
}
