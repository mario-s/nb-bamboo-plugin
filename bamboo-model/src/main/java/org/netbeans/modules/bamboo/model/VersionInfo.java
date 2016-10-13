package org.netbeans.modules.bamboo.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;



/**
 * @author spindizzy
 */
@Data
@Builder
public class VersionInfo {
    private String version;
    private int buildNumber;
    private LocalDate buildDate;
}
