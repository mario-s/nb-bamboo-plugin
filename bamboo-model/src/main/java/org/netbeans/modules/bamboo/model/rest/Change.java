package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Mario Schroeder
 */
@Data
@EqualsAndHashCode(of = "changesetId")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Change implements ServiceInfoProvideable{
    
    @XmlAttribute(name = "changesetId")
    private String changesetId;
    @XmlAttribute
    private String author;
    
    private String userName;
    private String fullName;
    
    private String comment;
    private String commitUrl;
    
    private String date;
    
    private Files files;
}
