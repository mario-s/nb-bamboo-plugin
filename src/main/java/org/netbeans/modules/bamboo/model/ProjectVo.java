package org.netbeans.modules.bamboo.model;

import java.util.List;
import lombok.Data;

/**
 * A class which represent the project.
 *
 * @author spindizzy
 */
@Data
public class ProjectVo {
    private String key;
    private String name;
    private List<PlanDto> plans;
}
