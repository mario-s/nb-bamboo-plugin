package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.rcp.FileVo;
import org.netbeans.modules.bamboo.model.rest.Files;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author spindizzy
 */
public class FilesVoConverter implements VoConverter<Files, Collection<FileVo>> {

    @Override
    public Collection<FileVo> convert(Files src) {
        if (src != null) {
            FileVoConverter converter = new FileVoConverter();
            return src.asCollection().stream().map(
                    f -> {
                        return converter.convert(f);
                    }).collect(toList());
        }
        return emptyList();
    }

}
