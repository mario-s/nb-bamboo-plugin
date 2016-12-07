package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author spindizzy
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractResponse<T> implements Responseable{
    private String expand;
    private Link link;
    
    @Override
    public int getMaxResult() {
        Metrics metrics = getMetrics();
        return (metrics != null) ? metrics.getMaxResult() : 0;
    }

    @Override
    public int getSize() {
        Metrics metrics = getMetrics();
        return (metrics != null) ? metrics.getSize() : 0;
    }
    
    protected abstract Metrics getMetrics();
}
