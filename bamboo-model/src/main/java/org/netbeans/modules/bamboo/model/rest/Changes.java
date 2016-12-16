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
 * @author Mario
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Changes extends Metrics implements Responseable<Change> {

    @XmlElement(name = "change")
    private List<Change> changes;

    @Override
    public Collection<Change> asCollection() {
        List<Change> coll = new ArrayList<>();
        if (changes != null) {
            coll.addAll(changes);
        }
        return coll;
    }
}
