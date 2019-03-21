Sentinel Consumer 降级规则(dataId 为 sentinel-degrade-rule):

```json
[
  {
    "resource": "GET:http://sca-provider/hello/sleep",
    "count": 20.0,
    "grade": 0,
    "timeWindow": 30
  },
  {
    "resource": "GET:http://sca-provider/hello/error",
    "count": 0.5,
    "grade": 1,
    "timeWindow": 30
  }
]
```

Sentinel Consumer 限流规则(dataId 为 sentinel-consumer-flow-rule)：

```json
[
  {
    "resource": "GET:http://sca-provider/config/info",
    "controlBehavior": 0,
    "count": 10,
    "grade": 1,
    "limitApp": "default",
    "strategy": 0
  },
  {
    "resource": "GET:http://localhost:20000/config/info",
    "controlBehavior": 0,
    "count": 10,
    "grade": 1,
    "limitApp": "default",
    "strategy": 0
  }
]
```

### 实验

1. 普通 resttemplate 调用：

http://localhost:30000/template

2. 负载均衡 resttemplate 调用：

http://localhost:30000/templateWithLB

3. feign 调用：

http://localhost:30000/sleep

4. error.sh 查看异常比例降级。

5. sleep.sh rt 降级。

6. resttemplate 和 feign 的客户端限流。

7. sca-provider 换个端口启动，查看负载均衡效果。

http://localhost:30000/configInfo

8. nacos 下线一个 sca-provider 实例再查看负载均衡效果。

http://localhost:30000/configInfo
