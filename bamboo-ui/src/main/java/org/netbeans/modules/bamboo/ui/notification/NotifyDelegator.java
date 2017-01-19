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
        EventQueue.invokeLater(new BuildResultNotifyDisplayer(ICON, buildResult));
    }
    
    void notify(QueueEvent event) {
        EventQueue.invokeLater(new QueueResultNotifyDisplayer(ICON, event));
    }
    
}
