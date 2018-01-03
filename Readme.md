## 更多内容： <http://www.bestwonderful.com>

## 这是一个rpc框架 由用户代理 wonderfulclient +  服务端 wonderfulserver 组成。


#### 实现了基础功能：
* post request
* http proxy
* application/json 或 x-www-form-urlencoded 类型request

#### 待实现功能：
* post 以外request
* request多参数支持
* socks proxy
* 基础认证
* 自定义加签
* 各种缓存

### 用法：
#### spring下：

+ 添加bean
~~~
<bean class="com.aikon.wht.spring.bean.ClientBeanFactory" id="clientBeanFactory">
        <constructor-arg>
            <bean class="com.aikon.wht.invocation.Configuration" id="configuration">
                <property name="host" value="localhost"/>
                <property name="port" value="9090"/>
                <property name="timeout" value="3000"/>
            </bean>
        </constructor-arg>
    </bean>

    <bean id="yourClient" factory-bean="clientBeanFactory" factory-method="create">
        <constructor-arg type="java.lang.Class" value="com.your.package.YourClient"/>
    </bean>
~~~    

+ 创建client
```
public interface YourClient {

    /**
     * POST /your/url HTTP1.1
     * host:localhost
     * port:9090
     * content-type : x-www-form-urlencoded
     * xxxx(entity)
     * 
     * @param request
     * @return Response
     */
    @BestWonderful("/your/url")
    Response aa(Request request);

    /**
     * POST /bb HTTP1.1
     * host:localhost
     * port:9090
     * content-type : x-www-form-urlencoded
     * xxxx(entity)
     *
     * @param request
     * @return Response
     */
    @BestWonderful
    Response bb(Request request);

    /**
     * POST /cc HTTP1.1
     * host:localhost
     * port:9090
     * content-type : application/json
     * xxxx(entity)
     *
     * spring中配合@ResquestBody使用
     * @param request
     * @return Response
     */
    @BestWonderful(json = true)
    Response cc(Request request);

}
```

+ 调用
```
YourClient testClient = applicationContext.getBean(YourClient.class);
Response response = testClient.cc(new Request());
```

### 非spring
+ 创建client
同上

+ 调用
```
Configuration configuration = new Configuration();
configuration.setHost("localhost");
configuration.setPort("9090");
InvocationHandler clientHandler = new DefaultClientHandler(configuration);
ClientProxyFactory.Builder builder = new ClientProxyFactory.Builder();
ClientProxyFactory clientProxyFactory = builder.invocationHandler(clientHandler).build();
YourClient testClient = clientProxyFactory.create(YourClient.class);
Response response = testClient.aa(new Request);
```

