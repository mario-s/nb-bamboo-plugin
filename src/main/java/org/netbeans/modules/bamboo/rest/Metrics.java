package org.netbeans.modules.bamboo.rest;

import org.codehaus.jackson.annotate.JsonProperty;


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

    public void setStartIndex(final int startIndex) {
        this.startIndex = startIndex;
    }

    public void setMaxResult(final int maxResult) {
        this.maxResult = maxResult;
    }

    public void setSize(final int size) {
        this.size = size;
    }
}
