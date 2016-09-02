package org.netbeans.modules.bamboo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author spindizzy
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractResponse<T> {
    private String expand;
    private Link link;
    
    public int getMaxResult() {
        Metrics metrics = getMetrics();
        return (metrics != null) ? metrics.getMaxResult() : 0;
    }

    public int getSize() {
        Metrics metrics = getMetrics();
        return (metrics != null) ? metrics.getSize() : 0;
    }
    
    protected abstract Metrics getMetrics();
    
    public abstract Collection<T> asCollection();
}
