package org.netbeans.modules.bamboo.model.rcp;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author Mario Schroeder
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionInfo {
    private String version;
    private int buildNumber;
    private LocalDateTime buildDate;
}
