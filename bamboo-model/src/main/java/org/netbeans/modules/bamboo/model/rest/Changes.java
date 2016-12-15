package org.netbeans.modules.bamboo.model.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Mario
 */
@Getter
@Setter
public class Changes extends Metrics implements Responseable<Change>{
    private List<Change> change;

    @Override
    public Collection<Change> asCollection() {
        List<Change> coll = new ArrayList<>();
        if(change != null) {
            coll.addAll(change);
        }
        return coll;
    }
}
