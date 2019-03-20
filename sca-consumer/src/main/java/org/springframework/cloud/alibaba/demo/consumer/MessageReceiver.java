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

import org.springframework.cloud.alibaba.demo.dubbo.service.User;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@Service
public class MessageReceiver {

    @StreamListener(Sink.INPUT)
    public void receiveMsg(Message msg) {
        System.out.println("receiveMsg, payload: " + msg.getPayload() + ", headers: " + msg.getHeaders());
    }

    @StreamListener(Sink.INPUT)
    public void receiveHeader(
        @Header(value = "index", required = false) String index,
        @Header(value = "location", required = false) String location
    ) {
        System.out.println("receiveHeader, index: " + index + ", location: " + location);
    }

    @StreamListener(value = Sink.INPUT, condition = "headers['object']=='1'")
    public void receiveHeader(@Payload User user) {
        System.out.println("receiveObject: " + user);
    }

}
