/*
 * Copyright 2022 NetBeans.
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
package org.netbeans.modules.bamboo.client.rest.call;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
abstract class AbstractWebTargetFactoryTest {

    protected static final String FOO = "foo";
    @Mock
    protected Client client;
    @Mock
    protected WebTarget target;
    @Mock
    protected InstanceValues values;

    protected void trainTarget() {
        given(client.target(FOO)).willReturn(target);
        given(target.path(AbstractWebTargetFactory.REST_API)).willReturn(target);
        given(target.path(FOO)).willReturn(target);
    }
    
}
