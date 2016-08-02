package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.modules.bamboo.glue.BuildProject;
import org.netbeans.modules.bamboo.glue.ProjectsProvideable;

import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

import java.util.Collection;
import java.util.List;


/**
 * @author spindizzy
 */
class ProjectNodeFactory extends ChildFactory<BuildProject> {
    private final ProjectsProvideable instance;

    private Collection<BuildProject> projects;

    ProjectNodeFactory(final ProjectsProvideable instance) {
        this.instance = instance;
        refreshNodes();
    }

    private void refreshNodes() {
        projects = instance.getProjects();
        refresh(true);
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

        return true;
    }
}
