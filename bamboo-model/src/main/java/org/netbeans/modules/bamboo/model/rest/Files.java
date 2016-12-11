package org.netbeans.modules.bamboo.model.rest;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author spindizzy
 */
@Getter
@Setter
public class Files extends Metrics{
    
   private List<File> files;
}
