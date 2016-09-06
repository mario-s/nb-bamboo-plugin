package org.netbeans.modules.bamboo.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author spindizzy
 */
@Getter
@Setter
public class Projects extends Metrics{
    List<Project> project;
}
