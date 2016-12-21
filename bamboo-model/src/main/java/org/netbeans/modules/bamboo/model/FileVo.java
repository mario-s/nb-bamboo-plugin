package org.netbeans.modules.bamboo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author spindizzy
 */
@Data
@EqualsAndHashCode(of = "revision")
public class FileVo {
    private String name;
    private String revision;
}
