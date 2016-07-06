package org.netbeans.modules.bamboo.rest.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author spindizzy
 */
@Getter
@Setter
abstract class AbstractAllResponse {
    private String expand;
    private Link link;
}
