package org.netbeans.modules.bamboo.convert;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author spindizzy
 */
abstract class AbstractVoConverter<S, T> implements VoConverter<S, T>{
    
    protected Optional<LocalDateTime> toDate(String str) {
        return new LocalDateTimeConverter().convert(str);
    }
}
