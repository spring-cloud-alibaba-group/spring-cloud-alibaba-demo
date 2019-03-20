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

package org.springframework.cloud.alibaba.demo.dubbo.execute;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.alibaba.demo.dubbo.service.EchoService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@RestController
public class DubboController {

    @Reference(version = "1.0.0")
    private EchoService echoService;

    @Autowired
    @Lazy
    private DubboFeignEchoService dubboFeignEchoService;

    @Autowired
    @Lazy
    private FeignEchoService feignEchoService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${provider.application.name}")
    private String serviceName;

    @GetMapping("/dubbofeign")
    public String dubbofeign() {
        return dubboFeignEchoService.echo("by dubbofeign");
    }

    @GetMapping("/dubbo")
    public String dubbo() {
        return echoService.echo("by dubbo");
    }

    @GetMapping("/feign")
    public String feign() {
        return feignEchoService.echo("by feign");
    }

    @GetMapping("/template")
    public String template() {
        return restTemplate.getForObject("http://" + serviceName + "/echo?param={p}", String.class, "by resttemplate");
    }

}
