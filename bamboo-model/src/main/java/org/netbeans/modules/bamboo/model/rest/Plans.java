package org.netbeans.modules.bamboo.model.rest;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plans extends Metrics {
    private List<Plan> plan;

}
