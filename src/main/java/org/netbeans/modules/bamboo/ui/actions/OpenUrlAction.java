package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.netbeans.modules.bamboo.glue.SharedConstants;
import org.netbeans.modules.bamboo.glue.OpenableInBrowser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

/**
 * This action opens the url of the give context in the prefered browser.
 * @author spindizzy
 */
@ActionID(
        category = "Team",
        id = "org.netbeans.modules.bamboo.ui.actions.OpenUrlAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenUrlAction"
)
@ActionReference(path = SharedConstants.ACTION_PATH, position = 600)
@Messages("CTL_OpenUrlAction=&Open in Browser")
public final class OpenUrlAction implements ActionListener {

    private final List<OpenableInBrowser> context;

    private URLDisplayer urlDisplayer;

    public OpenUrlAction(List<OpenableInBrowser> context) {
        this.context = context;
        urlDisplayer = URLDisplayer.getDefault();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        context.forEach(o -> {
            try {
                urlDisplayer.showURL(new URL(o.getUrl()));
            } catch (MalformedURLException ex) {
                Exceptions.printStackTrace(ex);
            }
        });
    }

    void setUrlDisplayer(URLDisplayer urlDisplayer) {
        this.urlDisplayer = urlDisplayer;
    }
}
