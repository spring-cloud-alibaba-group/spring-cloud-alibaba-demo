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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.alibaba.demo.dubbo.service.User;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@RestController
public class MessageController {

    private final Source source;

    private final ObjectMapper objectMapper;

    public MessageController(Source source, ObjectMapper objectMapper) {
        this.source = source;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/mq/send")
    public boolean send() {
        Map<String, Object> headersMap = new HashMap<>();
        headersMap.put("index", "1");
        headersMap.put("location", "beijing");
        Message msg = MessageBuilder.createMessage("msg from provider", new MessageHeaders(headersMap));
        source.output().send(msg);

        Map<String, Object> headersMap2 = new HashMap<>();
        headersMap2.put("object", "1");
        Message msg2 = null;
        try {
            msg2
                = MessageBuilder.createMessage(
                    objectMapper.writeValueAsString(new User(1L, "sca", 1)),
                    new MessageHeaders(headersMap2)
                );
        } catch (IOException e) {
            e.printStackTrace();
        }
        source.output().send(msg2);
        return true;
    }

}
