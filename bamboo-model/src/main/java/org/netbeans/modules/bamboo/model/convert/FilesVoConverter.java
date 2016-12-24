package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;
import java.util.stream.Collectors;
import org.netbeans.modules.bamboo.model.rcp.FileVo;
import org.netbeans.modules.bamboo.model.rest.Files;

import static java.util.Collections.emptyList;

/**
 *
 * @author spindizzy
 */
public class FilesVoConverter implements VoConverter<Files, Collection<FileVo>> {

    @Override
    public Collection<FileVo> convert(Files src) {
        if (src != null) {
            return src.asCollection().stream().map(
                    f -> {
                        FileVoConverter converter = new FileVoConverter();
                        return converter.convert(f);
                    }).collect(Collectors.toList());
        }
        return emptyList();
    }

}
