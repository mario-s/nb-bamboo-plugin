package org.netbeans.modules.bamboo.model;

import java.util.ArrayList;
import java.util.Collection;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This class contains extended information regarding a result of a plan. It will be loaded on demand.
 *
 * @author spindizzy
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class ResultDetailsVo extends ResultVo {

    private Collection<ChangeVo> changes;

    public ResultDetailsVo() {
        this("");
    }

    public ResultDetailsVo(String key) {
        super(key);
        changes = new ArrayList<>();
    }

    public void setChanges(Collection<ChangeVo> changes) {
        if (changes != null) {
            this.changes = changes;
        }
    }

}
