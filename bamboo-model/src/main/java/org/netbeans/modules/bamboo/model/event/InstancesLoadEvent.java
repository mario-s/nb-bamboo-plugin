package org.netbeans.modules.bamboo.model.event;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

/**
 * This event is fired when the saved {@link BambooInstance} are loaded from the user's preferences.
 *
 * @author Mario Schroeder
 */
@Getter
@AllArgsConstructor
public final class InstancesLoadEvent {

    @NonNull
    private final Collection<BambooInstance> instances;
}
