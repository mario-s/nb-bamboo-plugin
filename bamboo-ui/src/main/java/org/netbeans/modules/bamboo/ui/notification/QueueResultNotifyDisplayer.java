/*
 * Copyright 2016 NetBeans.
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
package org.netbeans.modules.bamboo.ui.notification;

import javax.swing.Icon;
import org.netbeans.modules.bamboo.model.QueueEvent;

/**
 *
 * @author spindizzy
 */
class QueueResultNotifyDisplayer extends AbstractNotifyDisplayer{
    
    private final QueueEvent event;

    QueueResultNotifyDisplayer(Icon icon, QueueEvent event) {
        super(icon);
        this.event = event;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
     
    
}
