package org.netbeans.modules.bamboo.glue;

import java.util.List;
import org.netbeans.modules.bamboo.rest.model.Result;

/**
 *
 * @author spindizzy
 */
public interface ResultsProvideable extends PlansProvideable{
    
    List<Result> getResults();
    
    void setResults(List<Result> results);
}
