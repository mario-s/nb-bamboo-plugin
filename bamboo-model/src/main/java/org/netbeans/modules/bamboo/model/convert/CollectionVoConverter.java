package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.rest.Responseable;


/**
 * A converter for collection.
 * @author spindizzy
 * @param <S> the source
 * @param <T> the target
 */
public class CollectionVoConverter<S extends Responseable, T> extends AbstractVoConverter <S, Collection<T>>{
    
    private final VoConverter<S, T> converter;

    public CollectionVoConverter(VoConverter<S, T> converter) {
        this.converter = converter;
    }
    
    @Override
    public Collection<T> convert(S src) {
        return convert(src, converter);
    }
    
}
