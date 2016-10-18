package org.netbeans.modules.bamboo.model.rest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Common parent class for all class which contain a uniqie identifier.
 * @author spindizzy
 */
@Getter
@Setter
@EqualsAndHashCode(of = "key")
public class Entity {
    private String key;
}
