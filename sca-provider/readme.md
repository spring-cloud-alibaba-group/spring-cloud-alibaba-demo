## 说明

sca-provider 和 sca-consumer 配合使用。

1. sca-provider：服务提供方。/hello 接口限流，使用 hello.sh 进行验证
2. sca-consumer：服务消费方。使用 feign 或 resttemplate 进行消费。同时进行降级测试，error.sh 验证异常降级，sleep.sh 验证rt降级
3. sca-provider 也是消息的发送方，sca-consumer 是消息的消费端。进行 Spring Cloud Stream 的验证

## 步骤

### nacos 启动

nacos 下载： https://github.com/alibaba/nacos/releases/download/1.0.0-RC1/nacos-server-1.0.0-RC1.zip

启动 nacos(默认端口8848，在 conf/application.properties 中修改)：

```bash
sh startup.sh -m standalone
```

启动成功之后访问 nacos dashboard(用户名密码 nacos/nacos):

http://localhost:8848/nacos

### sentinel 启动

sentinel dashboard 下载：https://github.com/alibaba/Sentinel/releases/download/1.4.2/sentinel-dashboard-1.4.2.jar

启动 sentinel dashboard，端口可修改:

```bash
java -Dserver.port=8080 -jar sentinel-dashboard-version.jar
```

启动成功之后访问 sentinel dashboard:

http://localhost:8080

### RocketMQ 启动

下载地址： https://www.apache.org/dyn/closer.cgi?path=rocketmq/4.4.0/rocketmq-all-4.4.0-bin-release.zip

启动 nameserver：

```bash
sh mqnamesrv
```

启动 broker：

```bash
sh mqbroker -n localhost:9876 -c ../conf/broker.conf
```

自动创建topic，在 broker.conf 中加入配置：

```properties
autoCreateTopicEnable = true
```

或手动创建：

```bash
./mqadmin updateTopic -n localhost:9876 -c DefaultCluster -t binder-topic
```

### 实验

Nacos 配置信息(dataId 为 sca-provider.properties)：

```properties
custom.config.location=bj
custom.config.date=2019-03-21 18:00:00
custom.config.room=405
```

请注意，在 `bootstrap.yml` 中配置 Nacos Config Server Address(不是在 `application.yml`)：

```yaml
spring:
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
```

访问 http://localhost:20000/config/info 查看配置信息是本地还是 nacos 上读取的。

访问 http://localhost:20000/config/custom?key=custom.config.topic 出现 unknown 。

nacos 修改配置：

```yaml
custom.config.topic=spring-cloud-alibaba
```

再次访问 http://localhost:20000/config/custom?key=custom.config.topic 出现 spring-cloud-alibaba 。


Sentinel 限流规则(dataId 为 sentinel-flow-rule)：

```json
[
  {
    "resource": "/hello",
    "controlBehavior": 0,
    "count": 3,
    "grade": 1,
    "limitApp": "default",
    "strategy": 0
  }
]
```

查看限流：

调用 hello.sh 后进入 sentinel dashboard 查看 qps 监控信息。

之后再 nacos 中调整 qps，查看 qps 监控信息。

查看配置：

进入 http://localhost:20000/config/info 查看配置信息是从本地配置文件读取的还是 nacos 读取的


## FAQ 

1. 如果发生了类似如下这个错误

```
failed to req API:/nacos/v1/ns/instance after all servers([localhost:8848]) tried: failed to req API:http://localhost:8848/nacos/v1/ns/instance. code:500 msg: ErrCode:400,ErrMsg:service not found, namespace: public, service: DEFAULT_GROUP@@sca-provider
```

请把 nacos 的启动方式改成 AP 模式。

```bash
curl -X PUT 'localhost:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=AP'
```


2. 启动多个应用 Provider 的话，intellij 勾掉 Single instance only 选项
