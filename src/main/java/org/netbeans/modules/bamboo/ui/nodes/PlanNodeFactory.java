package org.netbeans.modules.bamboo.ui.nodes;

import org.openide.nodes.Node;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import static java.util.Collections.sort;
import java.util.logging.Logger;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.openide.util.Lookup;

/**
 * @author spindizzy
 */
class PlanNodeFactory extends AbstractListenerChildFactory<PlanVo> {

    private static final Logger LOG = Logger.getLogger(PlanNodeFactory.class.getName());

    private static final PlanComparator COMPARATOR = new PlanComparator();

    private final ProjectVo project;

    private Collection<PlanVo> plans;

    private Lookup.Result<PlanVo> result;

    PlanNodeFactory(final ProjectVo project) {
        this.project = project;
        init();
    }

    private void init() {
        refreshNodes();
//        project.addPropertyChangeListener(this);
        result = project.getLookup().lookupResult(PlanVo.class);
        result.addLookupListener(this);
    }

    @Override
    void refreshNodes() {
        LOG.info(String.format("refreshing plans of %s", project.getName()));
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

    @Override
    void removePropertyChangeListener() {
//       project.removePropertyChangeListener(this);
    }

    private static class PlanComparator implements Comparator<PlanVo> {

        @Override
        public int compare(final PlanVo o1, final PlanVo o2) {
            final String left = o1.getName();
            final String right = o2.getName();

            return left.compareToIgnoreCase(right);
        }
    }
}
