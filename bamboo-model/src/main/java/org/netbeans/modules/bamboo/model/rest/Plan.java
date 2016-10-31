package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.netbeans.modules.bamboo.model.PlanType;

@Data
@EqualsAndHashCode(of = "key")
@JsonRootName(value = "plan")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Plan implements ServiceInfoProvideable{
    @XmlAttribute
    private String key;
    @XmlElement
    private Link link;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String shortKey;
    @XmlAttribute
    private String shortName;
    @XmlAttribute
    private boolean enabled;
    
    private transient PlanType type;
    private transient Result result;
    
}
