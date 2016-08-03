package org.netbeans.modules.bamboo.glue;

import lombok.Data;


/**
 * This class represent a Bamboo build project.
 *
 * @author spindizzy
 */
@Data
public class BuildProject {
    private String key;
    private String name;
    private String shortName;
    private transient boolean enabled;
}
