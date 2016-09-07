package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.model.Project;
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
class ProjectNodeFactory extends ChildFactory<Project> {
    private static final BuildProjectComparator COMPARATOR = new BuildProjectComparator();

    private final ProjectsProvideable instance;

    private Collection<Project> projects;

    ProjectNodeFactory(final ProjectsProvideable instance) {
        this.instance = instance;
        refreshNodes();
    }

    final void refreshNodes() {
        projects = instance.getProjects();
        refresh(false);
    }

    @Override
    protected Node createNodeForKey(final Project key) {
        return new ProjectNode(key);
    }

    @Override
    protected boolean createKeys(final List<Project> toPopulate) {
        if (projects != null) {
            toPopulate.addAll(projects);
        }

        sort(toPopulate, COMPARATOR);

        return true;
    }

    private static class BuildProjectComparator implements Comparator<Project> {
        @Override
        public int compare(final Project o1, final Project o2) {
            final String left = o1.getName();
            final String right = o2.getName();

            return left.compareToIgnoreCase(right);
        }
    }
}
