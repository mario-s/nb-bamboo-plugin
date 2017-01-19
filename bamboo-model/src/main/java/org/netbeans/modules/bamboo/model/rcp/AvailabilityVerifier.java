package org.netbeans.modules.bamboo.model.rcp;

import java.util.Optional;

/**
 *
 * @author Mario Schroeder
 */
final class AvailabilityVerifier {
    
    private AvailabilityVerifier() {
    }
    
    static boolean isAvailable(TraverseUp<? extends Availability> entity) {
       boolean available = false;
       Optional<? extends Availability> instance = entity.getParent();
       if(instance.isPresent()){
          available = instance.get().isAvailable();
       }
       return available;
    }
}
