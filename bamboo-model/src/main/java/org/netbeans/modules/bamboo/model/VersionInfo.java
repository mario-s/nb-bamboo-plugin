package org.netbeans.modules.bamboo.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author spindizzy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionInfo {
    private String version;
    private int buildNumber;
    private LocalDate buildDate;
}
