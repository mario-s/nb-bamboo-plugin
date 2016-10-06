package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Results extends Metrics {
    private List<Result> result;
    private String expand;
}
