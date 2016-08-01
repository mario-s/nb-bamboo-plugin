package org.netbeans.modules.bamboo.glue;

import lombok.Getter;
import lombok.Setter;


/**
 * This class represent a Bamboo build project.
 *
 * @author spindizzy
 */
@Getter
@Setter
public class BuildProject {
    private String name;
    private String shortName;
    private boolean enabled;
}
