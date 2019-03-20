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

import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@Configuration
public class ConsumerConfiguration {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate loadBalanceSentinelTemplate() {
        return new RestTemplate();
    }


    @Bean
    @SentinelRestTemplate
    //@SentinelRestTemplate(fallbackClass = FallbackHandler.class, fallback = "handle")
    public RestTemplate sentinelTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HelloServiceFallback helloServiceFallback() {
        return new HelloServiceFallback();
    }

    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

}
