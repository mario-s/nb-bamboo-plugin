package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;

/**
 *
 * @author spindizzy
 */
public class VersionInfoConverter extends AbstractVoConverter<Info, VersionInfo> {
    
    @Override
    public VersionInfo convert(Info src) {
        VersionInfo target = VersionInfo.builder().version(src.getVersion()).buildNumber(src.getBuildNumber()).build();
        final String buildDate = src.getBuildDate();
        toDate(buildDate).ifPresent((date) -> target.setBuildDate(date));
        return target;
    }
    
}
