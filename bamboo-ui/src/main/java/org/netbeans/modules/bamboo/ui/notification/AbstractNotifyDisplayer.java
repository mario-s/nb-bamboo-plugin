package org.netbeans.modules.bamboo.ui.notification;

import javax.swing.Icon;
import javax.swing.JComponent;
import org.netbeans.modules.bamboo.ui.HtmlPane;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.Pair;
import static org.openide.util.Pair.of;

/**
 *
 * @author spindizzy
 */
abstract class AbstractNotifyDisplayer implements Runnable {

    private final Icon icon;
    
    protected static final Pair<NotificationDisplayer.Priority, NotificationDisplayer.Category> ERROR = of(NotificationDisplayer.Priority.HIGH, NotificationDisplayer.Category.ERROR);
    protected static final Pair<NotificationDisplayer.Priority, NotificationDisplayer.Category> INFO = of(NotificationDisplayer.Priority.NORMAL, NotificationDisplayer.Category.INFO);
    

    AbstractNotifyDisplayer(Icon icon) {
        this.icon = icon;
    }

    protected Icon getIcon() {
        return icon;
    }

    protected JComponent newDetailsPanel(String summary, String buildReason) {
        HtmlPane reasonComp = new HtmlPane();
        reasonComp.setOpaque(true);
        reasonComp.setText(buildReason);
        return new ResultDetailsPanel(summary, reasonComp);
    }

    protected void notify(String name, JComponent balloonDetails, JComponent popupDetails, Pair<NotificationDisplayer.Priority, NotificationDisplayer.Category> cat) {
        getNotificationDisplayer().notify(name, getIcon(), balloonDetails, popupDetails, cat.first(), cat.second());
    }

    NotificationDisplayer getNotificationDisplayer() {
        return NotificationDisplayer.getDefault();
    }

    
}
