package org.netbeans.modules.bamboo.model.rest;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class Plans extends Metrics {
    private List<Plan> plan;

}
