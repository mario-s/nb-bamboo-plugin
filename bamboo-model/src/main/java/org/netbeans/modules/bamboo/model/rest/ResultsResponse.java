package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "results")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultsResponse extends AbstractResponse<Result> {
    private Results results;
    
    @Override
    public Collection<Result> asCollection(){
        Set<Result> coll = new HashSet<>();
        if(results != null){
            coll.addAll(results.getResult());
        }
        return coll;
    }

    @Override
    protected Metrics getMetrics() {
        return results;
    }
}
