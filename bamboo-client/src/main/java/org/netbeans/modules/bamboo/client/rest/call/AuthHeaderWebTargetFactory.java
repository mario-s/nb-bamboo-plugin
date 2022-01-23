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

import java.util.logging.Level;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

/**
 * This factory creates WebTraget which uses athorization in the header.
 * @author Mario Schroeder
 */
class AuthHeaderWebTargetFactory extends WebTargetFactory {

    AuthHeaderWebTargetFactory(InstanceValues values) {
        super(values);
    }

    AuthHeaderWebTargetFactory(InstanceValues values, Level level) {
        super(values, level);
    }
    
    //TODO override method
}
