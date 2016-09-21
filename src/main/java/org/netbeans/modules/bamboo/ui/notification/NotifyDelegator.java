package org.netbeans.modules.bamboo.ui.notification;

import java.awt.EventQueue;
import javax.swing.Icon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.openide.util.ImageUtilities;

/**
 *
 * @author spindizzy
 */
class NotifyDelegator {
    
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/instance.png";
    
    private static final Icon ICON = ImageUtilities.loadImageIcon(ICON_BASE, false);

    void notify(PlanVo plan) {
        EventQueue.invokeLater(new NotifyDisplayer(ICON, plan));
    }
    
}
