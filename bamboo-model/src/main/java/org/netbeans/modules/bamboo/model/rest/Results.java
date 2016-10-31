package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Results extends Metrics implements Responseable<Result>{
    private List<Result> result;
    private String expand;

    public Results() {
        result = new ArrayList<>();
    }

    @Override
    public Collection<Result> asCollection() {
        return result;
    }
    
}
