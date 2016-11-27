package org.netbeans.modules.bamboo.model.event;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.netbeans.modules.bamboo.model.BambooInstance;

/**
 * This event is fired when the saved {@link BambooInstance} are loaded from the user's preferences.
 *
 * @author spindizzy
 */
@Getter
@AllArgsConstructor
public class InstancesLoadEvent {

    Collection<BambooInstance> instances;
}
