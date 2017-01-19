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
 * @author Mario Schroeder
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Files extends Metrics implements Responseable<File> {

    @XmlElement(name = "file")
    private List<File> files;

    @Override
    public Collection<File> asCollection() {
        List<File> coll = new ArrayList<>();
        if (files != null) {
            coll.addAll(files);
        }
        return coll;
    }
}
