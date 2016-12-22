package org.netbeans.modules.bamboo.convert;

import java.util.Collection;
import java.util.stream.Collectors;
import org.netbeans.modules.bamboo.model.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Changes;

/**
 *
 * @author spindizzy
 */
public class ChangesVoConverter implements VoConverter<Changes, Collection<ChangeVo>> {

    @Override
    public Collection<ChangeVo> convert(Changes src) {

        return src.asCollection().stream().map(
                c -> {
                    ChangeVoConverter converter = new ChangeVoConverter();
                    return converter.convert(c);
                }).collect(Collectors.toList());

    }
}
