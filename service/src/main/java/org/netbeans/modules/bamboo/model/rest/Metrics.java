package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Metrics {
    @JsonProperty(value = "start-index")
    private int startIndex;
    @JsonProperty(value = "max-result")
    private int maxResult;
    private int size;
}
