package org.netbeans.modules.bamboo.glue;

import lombok.Data;

import java.util.Date;


/**
 * @author spindizzy
 */
@Data
public class VersionInfo {
    private String version;
    private int buildNumber;
    private Date buildDate;
}
