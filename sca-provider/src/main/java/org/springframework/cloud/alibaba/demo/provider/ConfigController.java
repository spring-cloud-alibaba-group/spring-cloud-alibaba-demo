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

package org.springframework.cloud.alibaba.demo.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    private final Environment env;

    @Value("${custom.config.location}")
    private String location;

    @Value("${custom.config.date}")
    private String date;

    @Value("${custom.config.room}")
    private Integer room;

    @Value("${server.port}")
    private Integer port;

    public ConfigController(Environment env) {
        this.env = env;
    }

    @GetMapping("/custom")
    public String custom(@RequestParam("key") String key) {
        return env.getProperty(key, "unknown");
    }

    @GetMapping("/info")
    public String configInfo() {
        return "location: " + location + ", date: " + date + ", room: " + room + ", port: " + port;
    }

}
