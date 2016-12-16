package org.netbeans.modules.bamboo.model.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author spindizzy
 */
@Getter
@Setter
public class Files extends Metrics implements Responseable<File>{
    
   private List<File> file;

    @Override
    public Collection<File> asCollection() {
        List<File> coll = new ArrayList<>();
        if(file != null) {
            coll.addAll(file);
        }
        return coll;
    }
}
