package com.aikon.wht.wonderful.rpc.client.http;

import com.aikon.wht.wonderful.rpc.client.invocation.Configuration;
import com.aikon.wht.wonderful.rpc.client.util.StringUtils;
import com.aikon.wht.wonderful.rpc.client.model.MethodWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * @author haitao.wang
 */
@Slf4j
public abstract class AbstractHttpExecutor implements HttpExecutor {

    Configuration configuration;

    public AbstractHttpExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    public String execute(MethodWrapper methodWrapper) throws Exception {

        HttpHost target = new HttpHost(configuration.getHost(),
                configuration.getPort() == null ? 80 : Integer.valueOf(configuration.getPort()),
                configuration.getSchema());

        HttpHost proxy = generateHttpProxy(configuration);


        HttpClientContext context = HttpClientContext.create();
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(configuration.getTimeout())
                .setSocketTimeout(configuration.getTimeout())
                .build();

//        CredentialsProvider credentialsProvider = socksProxy(context);

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm)
//                .setDefaultCredentialsProvider(credentialsProvider)
                .build();

        String response;
        try {
            HttpRequestBase httpRequest = generateHttpRequest(methodWrapper);
            httpRequest.setConfig(config);
            response = client.execute(target, httpRequest, new StringResponseHandler(), context);
        } finally {
            client.close();
        }
        return response;
    }


    protected HttpHost generateHttpProxy(Configuration configuration) {
        if (StringUtils.isBlank(configuration.getProxyHost()) || StringUtils.isBlank(configuration.getProxyPort())) {
            return null;
        }
        return new HttpHost(configuration.getProxyHost(), Integer.valueOf(configuration.getProxyPort()));
    }

    /**
     * 定制HttpRequest.
     *
     * @param methodWrapper
     * @return HttpRequestBase
     * @throws UnsupportedEncodingException
     */
    public abstract HttpRequestBase generateHttpRequest(MethodWrapper methodWrapper) throws UnsupportedEncodingException;






    static class MyConnectionSocketFactory implements ConnectionSocketFactory {

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(
                final int connectTimeout,
                final Socket socket,
                final HttpHost host,
                final InetSocketAddress remoteAddress,
                final InetSocketAddress localAddress,
                final HttpContext context) throws IOException, ConnectTimeoutException {
            Socket sock;
            if (socket != null) {
                sock = socket;
            } else {
                sock = createSocket(context);
            }
            if (localAddress != null) {
                sock.bind(localAddress);
            }
            try {
                sock.connect(remoteAddress, connectTimeout);
            } catch (SocketTimeoutException ex) {
                throw new ConnectTimeoutException(ex, host, remoteAddress.getAddress());
            }
            return sock;
        }

    }

    private CredentialsProvider socksProxy(HttpClientContext context) {
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new MyConnectionSocketFactory())
                .build();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("host", 1905),
                new UsernamePasswordCredentials("user", "password"));
        InetSocketAddress socksaddr = new InetSocketAddress("host", 1905);
        context.setAttribute("socks.address", socksaddr);
        return credentialsProvider;
    }
}
