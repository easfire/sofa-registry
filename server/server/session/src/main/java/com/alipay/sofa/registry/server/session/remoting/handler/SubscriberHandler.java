/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.registry.server.session.remoting.handler;

import com.alipay.sofa.registry.core.model.RegisterResponse;
import com.alipay.sofa.registry.core.model.SubscriberRegister;
import com.alipay.sofa.registry.remoting.Channel;
import com.alipay.sofa.registry.remoting.RemotingException;
import com.alipay.sofa.registry.server.session.scheduler.ExecutorManager;
import com.alipay.sofa.registry.server.session.strategy.SubscriberHandlerStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;

/**
 *
 * @author shangyu.wh
 * @version $Id: SubscriberHandler.java, v 0.1 2017-11-30 15:01 shangyu.wh Exp $
 */
public class SubscriberHandler extends AbstractServerHandler {
    @Autowired
    private ExecutorManager           executorManager;

    @Autowired
    private SubscriberHandlerStrategy subscriberHandlerStrategy;

    @Override
    public Object reply(Channel channel, Object message) throws RemotingException {
        RegisterResponse registerResponse = new RegisterResponse();
        SubscriberRegister subscriberRegister = (SubscriberRegister) message;
        subscriberHandlerStrategy.handleSubscriberRegister(channel, subscriberRegister,
            registerResponse);
        return registerResponse;
    }

    @Override
    public HandlerType getType() {
        return HandlerType.PROCESSER;
    }

    @Override
    public Class interest() {
        return SubscriberRegister.class;
    }

    @Override
    public Executor getExecutor() {
        return executorManager.getAccessDataExecutor();
    }

}