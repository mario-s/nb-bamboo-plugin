package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.glue.BambooInstance;

import java.util.Comparator;


/**
 * @author spindizzy
 */
final class BambooInstanceComparator implements Comparator<BambooInstance> {
    @Override
    public int compare(final BambooInstance o1, final BambooInstance o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
