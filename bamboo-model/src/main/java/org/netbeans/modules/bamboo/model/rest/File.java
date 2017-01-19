package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Mario Schroeder
 */
@Data
@EqualsAndHashCode(of = "revision")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class File {
    private String name;
    private String revision;
}
