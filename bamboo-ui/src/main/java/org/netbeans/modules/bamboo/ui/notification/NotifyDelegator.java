package org.netbeans.modules.bamboo.ui.notification;

import java.awt.EventQueue;
import javax.swing.Icon;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.util.ImageUtilities;

/**
 *
 * @author spindizzy
 */
class NotifyDelegator {
    
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";
    
    private static final Icon ICON = ImageUtilities.loadImageIcon(ICON_BASE, false);

    void notify(BuildResult buildResult) {
        EventQueue.invokeLater(new BuildResultNotifyDisplayer(ICON, buildResult));
    }
    
}
