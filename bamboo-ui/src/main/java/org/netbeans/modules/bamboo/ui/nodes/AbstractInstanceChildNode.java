package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.Action;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.event.ServerConnectionEvent;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * This is a parant class for all nodes which a children of a bamboo instance.
 *
 * @author spindizzy
 */
abstract class AbstractInstanceChildNode extends AbstractNode implements PropertyChangeListener, LookupListener {

    private Lookup.Result<ServerConnectionEvent> connectionLookupResult;

    AbstractInstanceChildNode(Lookup lookup) {
        super(Children.LEAF, lookup);
        addLookupListener();
    }

    private void addLookupListener() {
        connectionLookupResult = getLookup().lookupResult(ServerConnectionEvent.class);
        connectionLookupResult.addLookupListener(this);
    }

    @Override
    public void resultChanged(LookupEvent event) {
        Optional<BambooInstance> instance = getInstance();
        instance.ifPresent(inst -> {
            String name = inst.getName();
            Collection<? extends ServerConnectionEvent> events = connectionLookupResult.allInstances();
            List<? extends Action> actions = getToogleableActions();
            events.stream().filter(evt -> evt.getServerName().equals(name)).forEach(evt -> toggle(actions,
                    evt.isAvailable()));
        });
    }

    private void toggle(List<? extends Action> actions, boolean enabled) {
        actions.stream().filter(Objects::nonNull).forEach(ac -> ac.setEnabled(enabled));
    }

    protected abstract List<? extends Action> getToogleableActions();

    protected abstract Optional<BambooInstance> getInstance();

}
