package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Optional;
import static java.util.Optional.empty;
import org.netbeans.api.io.OutputWriter;
import org.netbeans.modules.bamboo.model.rcp.InstanceInvokeable;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.RequestProcessor;

/**
 *
 * @author spindizzy
 */
abstract class AbstractResultAction extends AbstractContextAction implements Runnable {

    private Lookup.Result<InstanceInvokeable> result;

    private Optional<PlanVo> plan = empty();

    public AbstractResultAction() {
    }

    public AbstractResultAction(String name, Lookup context) {
        super(name, context);
        init();
    }

    private void init() {
        result = getContext().lookupResult(InstanceInvokeable.class);
        result.addLookupListener(this);
        resultChanged(null);
        plan = empty();
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        enableIfAvailable(allInstances());
    }

    protected Collection<? extends InstanceInvokeable> allInstances() {
        return result.allInstances();
    }

    protected Optional<PlanVo> findFirst() {
        return (Optional<PlanVo>) allInstances().stream().findFirst();
    }

    protected OutputWriter getOut(String name) {
        InputOutputProvider provider = new InputOutputProvider();
        return provider.getOut(name);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        plan = findFirst();
        plan.ifPresent(p -> new RequestProcessor(getClass()).post(this));
    }
    
    @Override
    public void run() {
        plan.ifPresent(p -> doRun(p));
    }
    
    protected abstract void doRun(PlanVo p);


}
