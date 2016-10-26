package org.netbeans.modules.bamboo.model;

import java.util.Optional;

/**
 *
 * @author spindizzy
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
