package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

import org.openide.nodes.Node;

import java.util.Collection;
import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.openide.util.Lookup;

/**
 * @author spindizzy
 */
class ProjectNodeFactory extends AbstractListenerChildFactory<ProjectVo> {

    private static final Logger LOG = Logger.getLogger(ProjectNodeFactory.class.getName());

    private static final BuildProjectComparator COMPARATOR = new BuildProjectComparator();

    private final ProjectsProvideable instance;

    private Collection<ProjectVo> projects;

    private Lookup.Result<ProjectVo> result;

    ProjectNodeFactory(final ProjectsProvideable instance) {
        this.instance = instance;

        init();
    }

    private void init() {
        refreshNodes();
        //instance.addPropertyChangeListener(this);
        result = instance.getLookup().lookupResult(ProjectVo.class);
        result.addLookupListener(this);
    }

    @Override
    void refreshNodes() {
        LOG.info(String.format("refreshing projects of %s", instance.getName()));
        projects = instance.getProjects();
        refresh(false);
    }

    @Override
    protected Node createNodeForKey(final ProjectVo key) {
        return new ProjectNode(key);
    }

    @Override
    protected boolean createKeys(final List<ProjectVo> toPopulate) {
        if (projects != null) {
            toPopulate.addAll(projects);
        }

        sort(toPopulate, COMPARATOR);

        return true;
    }

    @Override
    void removePropertyChangeListener() {
//        instance.removePropertyChangeListener(this);
    }

    private static class BuildProjectComparator implements Comparator<ProjectVo> {

        @Override
        public int compare(final ProjectVo o1, final ProjectVo o2) {
            final String left = o1.getName();
            final String right = o2.getName();

            return left.compareToIgnoreCase(right);
        }
    }
}
