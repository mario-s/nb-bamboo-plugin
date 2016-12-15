package org.netbeans.modules.bamboo.model.rest;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Mario
 */
@Getter
@Setter
public class Changes extends Metrics{
    private List<Change> change;
}
