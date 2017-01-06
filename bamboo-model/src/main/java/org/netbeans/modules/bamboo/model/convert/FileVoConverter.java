package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.FileVo;
import org.netbeans.modules.bamboo.model.rest.File;

/**
 *
 * @author spindizzy
 */
public class FileVoConverter extends AbstractVoConverter<File, FileVo>{

    @Override
    public FileVo convert(File src) {
        FileVo target = new FileVo();
        copyProperties(src, target);
        return target;
    }
    
}
