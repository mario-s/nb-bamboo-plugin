package org.netbeans.modules.bamboo.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.netbeans.modules.bamboo.model.BambooInstance;

/**
 * This event is fired when the connection to a CI server is lost or it comes back to live.
 * @author spindizzy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerConnectionEvent {
   private BambooInstance instance;
}
