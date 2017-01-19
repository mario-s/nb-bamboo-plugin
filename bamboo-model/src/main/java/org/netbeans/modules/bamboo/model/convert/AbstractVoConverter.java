package org.netbeans.modules.bamboo.model.convert;

import java.time.LocalDateTime;
import java.util.Optional;
import static jodd.bean.BeanCopy.beans;

/**
 *
 * @author Mario Schroeder
 */
abstract class AbstractVoConverter<S, T> implements VoConverter<S, T> {

    protected Optional<LocalDateTime> toDate(String str) {
        return new LocalDateTimeConverter().convert(str);
    }
    
    /**
     * Copies properties from source to target.
     * @param source source object
     * @param target target object
     */
    protected void copyProperties(S source, T target) {
        beans(source, target).copy();
    }
   
}
