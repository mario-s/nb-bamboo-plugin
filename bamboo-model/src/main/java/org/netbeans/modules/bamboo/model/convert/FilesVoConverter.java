package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.rcp.FileVo;
import org.netbeans.modules.bamboo.model.rest.Files;


/**
 *
 * @author spindizzy
 */
public class FilesVoConverter extends AbstractVoConverter<Files, Collection<FileVo>> {

    @Override
    public Collection<FileVo> convert(Files src) {
        return convert(src, new FileVoConverter());
    }

}
