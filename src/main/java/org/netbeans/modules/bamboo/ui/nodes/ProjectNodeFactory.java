package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.model.BuildProject;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import java.util.Collection;
import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.List;


/**
 * @author spindizzy
 */
class ProjectNodeFactory extends ChildFactory<BuildProject> {
    private static final BuildProjectComparator COMPARATOR = new BuildProjectComparator();

    private final ProjectsProvideable instance;

    private Collection<BuildProject> projects;

    ProjectNodeFactory(final ProjectsProvideable instance) {
        this.instance = instance;
        refreshNodes();
    }

    final void refreshNodes() {
        projects = instance.getProjects();
        refresh(false);
    }

    @Override
    protected Node createNodeForKey(final BuildProject key) {
        return new ProjectNode(key);
    }

    @Override
    protected boolean createKeys(final List<BuildProject> toPopulate) {
        if (projects != null) {
            toPopulate.addAll(projects);
        }

        sort(toPopulate, COMPARATOR);

        return true;
    }

    private static class BuildProjectComparator implements Comparator<BuildProject> {
        @Override
        public int compare(final BuildProject o1, final BuildProject o2) {
            final String left = o1.getName();
            final String right = o2.getName();

            return left.compareToIgnoreCase(right);
        }
    }
}
