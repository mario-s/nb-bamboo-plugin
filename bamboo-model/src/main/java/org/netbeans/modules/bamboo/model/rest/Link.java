package org.netbeans.modules.bamboo.model.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {
    @XmlAttribute
    private String href;
    @XmlAttribute
    private String rel;
}
