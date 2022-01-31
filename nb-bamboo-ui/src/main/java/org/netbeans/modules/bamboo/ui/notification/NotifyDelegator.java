/* 
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

import java.awt.EventQueue;
import javax.swing.Icon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.openide.util.ImageUtilities;

/**
 * A delegator for the notification.
 * @author Mario Schroeder
 */
class NotifyDelegator {
    
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";
    
    private static final Icon ICON = ImageUtilities.loadImageIcon(ICON_BASE, false);

    void notify(BuildResult buildResult) {
        EventQueue.invokeLater(new BuildResultNotifyDisplay(ICON, buildResult));
    }
    
    void notify(QueueEvent event) {
        EventQueue.invokeLater(new QueueResultNotifyDisplay(ICON, event));
    }
    
}
