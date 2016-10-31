package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.State;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Result implements ServiceInfoProvideable{
    @XmlAttribute
    private String key;
    @XmlElement
    private Link link;
    @XmlAttribute
    private State state;
    @XmlElement(name = "plan")
    private Plan plan;
    @XmlAttribute
    private LifeCycleState lifeCycleState;
    @XmlAttribute
    private int number;
    private int id;
    private String buildReason;
    
    public Result() {
        state = State.Unknown;
        lifeCycleState = LifeCycleState.NotBuilt;
    }
}
