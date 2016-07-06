package org.netbeans.modules.bamboo.rest.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author spindizzy
 */
@Getter
@Setter
public class AllPlansResponse extends AbstractAllResponse {
    private Plans plans;

}
