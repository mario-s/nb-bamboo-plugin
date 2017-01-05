package org.netbeans.modules.bamboo.ui.actions;

import java.util.Collection;
import java.util.Optional;
import javax.swing.AbstractAction;
import org.netbeans.modules.bamboo.model.rcp.Availability;
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

    protected void enableIfAvailable(Collection<? extends Availability> instances) {
        Optional<? extends Availability> opt = instances.stream().filter(p -> p.isAvailable()).findAny();
        setEnabled(opt.isPresent());
    }
}
