package org.netbeans.modules.bamboo.ui.nodes;

import java.io.Serializable;

import org.openide.nodes.Node;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;

import static java.util.Collections.sort;

/**
 * This factory creates a new {@link ProjectNode}.
 * 
 * @author Mario Schroeder
 */
@Log
class ProjectNodeFactory extends AbstractRefreshChildFactory<ProjectVo> {

    private static final BuildProjectComparator COMPARATOR = new BuildProjectComparator();
    
    private final BambooInstance instance;

    private Collection<ProjectVo> projects;

    ProjectNodeFactory(final BambooInstance instance) {
        this.instance = instance;
        init();
    }

    private void init() {
        refreshNodes();
    }

    @Override
    void refreshNodes() {
        log.info(String.format("refreshing projects of %s", instance.getName()));
        projects = instance.getChildren();
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

    private static class BuildProjectComparator implements Comparator<ProjectVo>, Serializable {

        private static final long serialVersionUID = 1L;
        
        @Override
        public int compare(final ProjectVo o1, final ProjectVo o2) {
            final String left = o1.getName();
            final String right = o2.getName();

            return left.compareToIgnoreCase(right);
        }
    }
}
