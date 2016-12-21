package org.netbeans.modules.bamboo.convert;

import org.netbeans.modules.bamboo.model.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;

/**
 *
 * @author spindizzy
 */
public class VersionInfoConverter implements VoConverter<Info, VersionInfo> {
    
    @Override
    public VersionInfo convert(Info src) {
        VersionInfo target = VersionInfo.builder().version(src.getVersion()).buildNumber(src.getBuildNumber()).build();
        final String buildDate = src.getBuildDate();
        LocalDateTimeConverter dateConverter = new LocalDateTimeConverter();
        dateConverter.convert(buildDate).ifPresent((date) -> target.setBuildDate(date));
        return target;
    }
    
}
