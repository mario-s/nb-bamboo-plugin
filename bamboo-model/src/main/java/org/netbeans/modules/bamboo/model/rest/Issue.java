package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Mario Schroeder
 */
@Data
@EqualsAndHashCode(of = "key")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Issue {
    @XmlAttribute
    private String key;
    @XmlAttribute
    private String summary;
    @XmlAttribute
    private String iconUrl;
    @XmlAttribute
    private String assignee;
    @XmlElement(name = "url")
    private Link link;
}
