/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.rest.Responseable;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * A converter for collection.
 *
 * @author Mario Schroeder
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
        return collection.stream().map(converter::convert).collect(toList());
    }

}
