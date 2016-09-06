package org.netbeans.modules.bamboo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

/**
 *
 * @author spindizzy
 */
@Data
@JsonRootName(value = "project")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
    private String key;
    private Link link;
    private String name;
    private transient Plans plans;
}
