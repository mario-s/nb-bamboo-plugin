package org.netbeans.modules.bamboo.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metrics {

    @JsonProperty("start-index")
    private int startIndex;
    @JsonProperty("max-result")
    private int maxResult;
    private int size;

    public int getStartIndex() {
        return startIndex;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public int getSize() {
        return size;
    }
}
