package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.OpenableInBrowser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Team",
        id = "org.netbeans.modules.bamboo.ui.actions.OpenUrlAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenUrlAction"
)
@ActionReference(path = BambooInstance.ACTION_PATH, position = 600)
@Messages("CTL_OpenUrlAction=&Open in Browser")
public class OpenUrlAction implements ActionListener {

    private final List<OpenableInBrowser> openables;

    public OpenUrlAction(List<OpenableInBrowser> openables) {
        this.openables = openables;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openables.forEach(o -> {
            try {
                URL url = new URL(o.getUrl());
                getUrlDisplayer().showURL(url);
            } catch (MalformedURLException ex) {
                Exceptions.printStackTrace(ex);
            }
        });
    }

    URLDisplayer getUrlDisplayer() {
        return URLDisplayer.getDefault();
    }
}
