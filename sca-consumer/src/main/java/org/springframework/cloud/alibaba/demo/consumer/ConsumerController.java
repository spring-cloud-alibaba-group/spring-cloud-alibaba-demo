/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.alibaba.demo.consumer;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@RestController
public class ConsumerController {

    private final RestTemplate loadBalanceSentinelTemplate;

    private final RestTemplate sentinelTemplate;

    private final HelloService helloService;

    private final Environment env;

    public ConsumerController(RestTemplate loadBalanceSentinelTemplate,
                              RestTemplate sentinelTemplate,
                              HelloService helloService, Environment env) {
        this.loadBalanceSentinelTemplate = loadBalanceSentinelTemplate;
        this.sentinelTemplate = sentinelTemplate;
        this.helloService = helloService;
        this.env = env;
    }

    @GetMapping("/template")
    public String template() {
        return sentinelTemplate.getForObject("http://localhost:20000/config/info", String.class);
    }

    @GetMapping("/templateWithLB")
    public String templateWithLB() {
        return loadBalanceSentinelTemplate.getForObject("http://sca-provider/config/info", String.class);
    }

    @GetMapping("/err")
    public String error() {
        return helloService.error();
    }

    @GetMapping("/sleep")
    public String sleep() {
        return helloService.sleep();
    }

    @GetMapping("/configInfo")
    public String config() {
        return helloService.configInfo();
    }

    @GetMapping("/config/custom")
    public String custom(@RequestParam String key) {
        return env.getProperty(key, "unknown");
    }

}
