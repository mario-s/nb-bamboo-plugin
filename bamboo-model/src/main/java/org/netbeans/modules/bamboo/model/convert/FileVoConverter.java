package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.FileVo;
import org.netbeans.modules.bamboo.model.rest.File;

/**
 *
 * @author spindizzy
 */
public class FileVoConverter implements VoConverter<File, FileVo>{

    @Override
    public FileVo convert(File src) {
        FileVo target = new FileVo();
        target.setName(src.getName());
        target.setRevision(src.getRevision());
        return target;
    }
    
}
