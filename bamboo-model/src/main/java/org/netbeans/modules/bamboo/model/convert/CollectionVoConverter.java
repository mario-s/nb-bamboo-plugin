package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.rest.Responseable;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * A converter for collection.
 *
 * @author spindizzy
 * @param <S> the source
 * @param <T> the target
 */
public class CollectionVoConverter<S extends Responseable, T> extends AbstractVoConverter<S, Collection<T>> {

    private final VoConverter<S, T> converter;

    public CollectionVoConverter(VoConverter<S, T> converter) {
        this.converter = converter;
    }

    @Override
    public Collection<T> convert(S src) {
        return convert(src, converter);
    }

    private <S, T> Collection<T> convert(final Responseable<S> source, VoConverter<S, T> converter) {
        return (source != null) ? convert(source.asCollection(), converter) : emptyList();
    }

    private <S, T> Collection<T> convert(final Collection<S> collection, VoConverter<S, T> converter) {
        return collection.stream().map(
                c -> {
                    return converter.convert(c);
                }).collect(toList());
    }

}
