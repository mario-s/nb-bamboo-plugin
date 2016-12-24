package org.netbeans.modules.bamboo.ui.actions;

import javax.swing.AbstractAction;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 *
 * @author spindizzy
 */
abstract class AbstractContextAction extends AbstractAction implements LookupListener, ContextAwareAction {

    private Lookup context;

    public AbstractContextAction() {
        this("", Utilities.actionsGlobalContext());
    }

    AbstractContextAction(String name, Lookup context) {
        super(name);
        this.context = context;
    }

    protected Lookup getContext() {
        return context;
    }

}
