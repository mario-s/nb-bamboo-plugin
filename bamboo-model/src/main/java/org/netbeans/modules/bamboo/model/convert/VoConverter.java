package org.netbeans.modules.bamboo.model.convert;

/**
 * Interface for class which convert class from the rest model to class for model to be used for the view.
 *
 * @param <S> the target to convert into
 * @param <T> the source to convert from
 *
 * @author Mario Schroeder
 */
public interface VoConverter<S, T> {

    T convert(S src);




}
