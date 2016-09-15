package org.netbeans.modules.bamboo.glue;

import java.time.LocalDate;
import lombok.Data;



/**
 * @author spindizzy
 */
@Data
public class VersionInfo {
    private String version;
    private int buildNumber;
    private LocalDate buildDate;
}
