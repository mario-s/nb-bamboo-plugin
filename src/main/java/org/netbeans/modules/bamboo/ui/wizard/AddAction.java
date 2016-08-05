package org.netbeans.modules.bamboo.ui.wizard;

import static org.netbeans.modules.bamboo.ui.wizard.Bundle.TXT_ADD;


import org.openide.util.NbBundle;


/**
 * @author spindizzy
 */
@NbBundle.Messages({ "TXT_ADD=OK" })
class AddAction extends AbstractDialogAction {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;

    private final InstancePropertiesForm form;

    private AddInstanceWorker worker;

    public AddAction(final InstancePropertiesForm form) {
        super(TXT_ADD());
        this.form = form;
        init();
    }

    private void init() {
        worker = new AddInstanceWorker(this);
        disable();
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
    protected void onCancel() {
        super.onCancel();
        worker.cancel();
    }
}
