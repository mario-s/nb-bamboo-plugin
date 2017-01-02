package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author spindizzy
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class JiraIssues extends Metrics implements Responseable<Issue>{
    
    @XmlElement(name = "issue")
    private List<Issue> issues;

    @Override
    public Collection<Issue> asCollection() {
        List<Issue> coll = new ArrayList<>();
        if (issues != null) {
            coll.addAll(issues);
        }
        return coll;
    }
    
}
