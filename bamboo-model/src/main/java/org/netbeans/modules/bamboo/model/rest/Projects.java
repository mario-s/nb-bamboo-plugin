package org.netbeans.modules.bamboo.model.rest;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Mario Schroeder
 */
@Getter
@Setter
public class Projects extends Metrics{
    List<Project> project;
}
