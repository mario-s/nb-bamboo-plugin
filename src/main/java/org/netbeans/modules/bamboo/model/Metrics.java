package org.netbeans.modules.bamboo.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Metrics {
    @JsonProperty(value = "start-index")
    private int startIndex;
    @JsonProperty(value = "max-result")
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
