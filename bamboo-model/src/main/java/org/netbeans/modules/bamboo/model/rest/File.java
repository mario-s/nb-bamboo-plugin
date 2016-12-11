package org.netbeans.modules.bamboo.model.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author spindizzy
 */
@Data
@EqualsAndHashCode(of = "revision")
public class File {
    private String name;
    private String revision;
}
