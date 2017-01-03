package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;

import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Changes;

/**
 *
 * @author spindizzy
 */
public class ChangesVoConverter extends AbstractVoConverter<Changes, Collection<ChangeVo>> {

    @Override
    public Collection<ChangeVo> convert(Changes src) {
        return convert(src, new ChangeVoConverter());
    }
}
