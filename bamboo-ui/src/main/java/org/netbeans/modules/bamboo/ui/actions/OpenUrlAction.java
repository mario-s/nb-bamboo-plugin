package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import java.util.Optional;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.modules.bamboo.model.OpenableInBrowser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * This action opens the url of the give openable in the prefered browser.
 *
 * @author spindizzy
 */
@ActionID(
        category = "Bamboo",
        id = "org.netbeans.modules.bamboo.ui.actions.OpenUrlAction"
)
@ActionRegistration(
        displayName = "#CTL_OpenUrlAction", lazy = true
)
@ActionReference(path = ActionConstants.ACTION_PATH, position = 600)
@Messages("CTL_OpenUrlAction=&Open in Browser")
public final class OpenUrlAction extends AbstractAction implements LookupListener, ContextAwareAction {

    private Lookup context;

    private Lookup.Result<OpenableInBrowser> result;

    private URLDisplayer urlDisplayer;

    public OpenUrlAction() {
        this(Utilities.actionsGlobalContext());
    }

    OpenUrlAction(Lookup context) {
        super(Bundle.CTL_QueuePlanAction());
        this.context = context;
        urlDisplayer = URLDisplayer.getDefault();
        init();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        allInstances().forEach(o -> {
            try {
                urlDisplayer.showURL(new URL(o.getUrl()));
            } catch (MalformedURLException ex) {
                Exceptions.printStackTrace(ex);
            }
        });
    }

    private Collection<? extends OpenableInBrowser> allInstances() {
        return result.allInstances();
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new OpenUrlAction(actionContext);
    }

    private void init() {
        result = context.lookupResult(OpenableInBrowser.class);
        result.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Optional<? extends OpenableInBrowser> opt = allInstances().stream().filter(p -> p.isAvailable()).findAny();
        setEnabled(opt.isPresent());
    }

    void setUrlDisplayer(URLDisplayer urlDisplayer) {
        this.urlDisplayer = urlDisplayer;
    }

}
