package org.netbeans.modules.bamboo.ui.nodes;

import java.io.Serializable;
import org.openide.nodes.Node;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import static java.util.Collections.sort;
import lombok.extern.java.Log;

/**
 * @author spindizzy
 */
@Log
class PlanNodeFactory extends AbstractRefreshChildFactory<PlanVo> {

    private static final PlanComparator COMPARATOR = new PlanComparator();

    private final ProjectVo project;

    private Collection<PlanVo> plans;

    PlanNodeFactory(final ProjectVo project) {
        this.project = project;
        init();
    }

    private void init() {
        refreshNodes();
    }

    @Override
    void refreshNodes() {
        log.info(String.format("refreshing plans of %s", project.getName()));
        plans = project.getPlans();
        refresh(false);
    }

    @Override
    protected Node createNodeForKey(final PlanVo key) {
        return new PlanNode(key);
    }

    @Override
    protected boolean createKeys(final List<PlanVo> toPopulate) {
        if (plans != null) {
            toPopulate.addAll(plans);
        }

        sort(toPopulate, COMPARATOR);

        return true;
    }

    private static class PlanComparator implements Comparator<PlanVo>, Serializable {

        private static final long serialVersionUID = 1L;
        
        @Override
        public int compare(final PlanVo o1, final PlanVo o2) {
            final String left = o1.getName();
            final String right = o2.getName();

            return left.compareToIgnoreCase(right);
        }
    }
}