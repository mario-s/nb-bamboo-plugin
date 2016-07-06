package org.netbeans.modules.bamboo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author spindizzy
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class AbstractResponse {
    private String expand;
    private Link link;
}
