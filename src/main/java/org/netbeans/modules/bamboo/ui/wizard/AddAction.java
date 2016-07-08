package org.netbeans.modules.bamboo.ui.wizard;

import static org.netbeans.modules.bamboo.ui.wizard.Bundle.TXT_ADD;

import org.openide.util.NbBundle;

import org.netbeans.modules.bamboo.glue.InstanceManageable;

import org.netbeans.modules.bamboo.glue.PlansProvideable;
import org.netbeans.modules.bamboo.glue.SharedConstants;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * @author spindizzy
 */
@NbBundle.Messages({"TXT_ADD=OK"})
class AddAction extends AbstractDialogAction implements LookupListener {

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;


    private final InstancePropertiesForm form;
    
    private Lookup.Result<PlansProvideable> result;
    
    private AddInstanceWorker worker;

    public AddAction(final InstancePropertiesForm form) {
        super(TXT_ADD());
        this.form = form;
        addLookup();
        disable();
    }

    private void addLookup() {
        InstanceManageable manager = getInstanceManager();
        result = manager.getLookup().lookupResult(PlansProvideable.class);
        result.addLookupListener(this);
        resultChanged(null);
        worker = new AddInstanceWorker(this);
    }

    private void disable() {
        setEnabled(false);
    }

    @Override
    protected void onOk() {
        disable(); // block from clicking again
        addInstance();
    }

    private void addInstance() {
        form.block();
        worker.execute(form);
    }

    @Override
    protected void onDone() {
        firePropertyChange(SharedConstants.PROCESS_DONE, null, NotifyDescriptor.OK_OPTION);
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        worker.cancel(true);
    }

    @Override
    public void resultChanged(LookupEvent le) {
        if (le != null) {
            form.unblock();
        }
    }
}
