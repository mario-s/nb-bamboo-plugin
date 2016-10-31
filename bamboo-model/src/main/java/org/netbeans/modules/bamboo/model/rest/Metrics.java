package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Metrics {
    @JsonProperty(value = "start-index")
    @XmlAttribute
    private int startIndex;
    @JsonProperty(value = "max-result")
    @XmlAttribute
    private int maxResult;
    @XmlAttribute
    private int size;
}
