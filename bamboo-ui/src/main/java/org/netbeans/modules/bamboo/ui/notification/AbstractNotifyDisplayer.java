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

import javax.swing.Icon;
import javax.swing.JComponent;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

import static org.openide.util.Pair.of;

/**
 * Parent for classes which want to show a notification related to the Bamboo instance.
 * 
 * @author Mario Schroeder
 */
@NbBundle.Messages({
    "Build=The Build"
})
abstract class AbstractNotifyDisplayer implements Runnable {

    private final Icon icon;
    
    /**
     * Error category.
     */
    protected static final Pair<NotificationDisplayer.Priority, NotificationDisplayer.Category> ERROR = of(NotificationDisplayer.Priority.HIGH, NotificationDisplayer.Category.ERROR);
    
    /**
     * Info category
     */
    protected static final Pair<NotificationDisplayer.Priority, NotificationDisplayer.Category> INFO = of(NotificationDisplayer.Priority.NORMAL, NotificationDisplayer.Category.INFO);
    

    AbstractNotifyDisplayer(Icon icon) {
        this.icon = icon;
    }

    /**
     * This method returns the icon for the notification.
     * @return the icon to be used to the notification
     */
    protected Icon getIcon() {
        return icon;
    }

    /**
     * This method creates a new componet to show the details.
     * @param summary a short summary
     * @param buildReason more explanation
     * @return a new {@link JComponent}
     */
    protected JComponent newDetailsComponent(String summary, String buildReason) {
        ResultDetailsPanelFactory factory = new ResultDetailsPanelFactory();
        return factory.create(summary, buildReason);
    }

    /**
     * Does the actually notification based on NetBeans' {@link NotificationDisplayer}. 
     * @param name The name of the notification.
     * @param balloonDetails details for the baloon
     * @param popupDetails details for the popup
     * @param cat the category.
     */
    protected void notify(String name, JComponent balloonDetails, JComponent popupDetails, Pair<NotificationDisplayer.Priority, NotificationDisplayer.Category> cat) {
        getNotificationDisplayer().notify(name, getIcon(), balloonDetails, popupDetails, cat.first(), cat.second());
    }

    NotificationDisplayer getNotificationDisplayer() {
        return NotificationDisplayer.getDefault();
    }

    
}
