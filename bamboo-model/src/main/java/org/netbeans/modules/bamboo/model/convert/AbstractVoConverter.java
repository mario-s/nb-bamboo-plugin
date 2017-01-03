package org.netbeans.modules.bamboo.model.convert;

import java.time.LocalDateTime;
import java.util.Collection;
import static java.util.Collections.emptyList;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import org.netbeans.modules.bamboo.model.rest.Responseable;

/**
 *
 * @author spindizzy
 */
abstract class AbstractVoConverter<S, T> implements VoConverter<S, T> {

    protected Optional<LocalDateTime> toDate(String str) {
        return new LocalDateTimeConverter().convert(str);
    }
    
    protected <S, T> Collection<T> convert(final Responseable<S> source, VoConverter<S, T> converter) {
        return (source != null) ? convert(source.asCollection(), converter) : emptyList();
    }

    protected <S, T> Collection<T> convert(final Collection<S> collection, VoConverter<S, T> converter) {
        return collection.stream().map(
                c -> {
                    return converter.convert(c);
                }).collect(toList());
    }
}
