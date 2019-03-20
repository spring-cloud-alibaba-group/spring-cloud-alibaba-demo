package org.springframework.cloud.alibaba.demo.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@FeignClient(name = "sca-provider", fallback = HelloServiceFallback.class)
public interface HelloService {

    @GetMapping(value = "/hello/error")
    String error();

    @GetMapping(value = "/hello/sleep")
    String sleep();

    @GetMapping(value = "/config/info")
    String configInfo();

    @GetMapping(value = "/config/custom")
    String config(String key);

}

class HelloServiceFallback implements HelloService {

    @Override
    public String error() {
        return "error fallback";
    }

    @Override
    public String sleep() {
        return "sleep fallback";
    }

    @Override
    public String configInfo() {
        return "configInfo fallback";
    }

    @Override
    public String config(String key) {
        return "config fallback";
    }
}
