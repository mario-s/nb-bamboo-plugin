package org.netbeans.modules.bamboo.ui.nodes;


import org.netbeans.modules.bamboo.model.Project;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.ui.actions.OpenUrlAction;
import org.openide.util.lookup.Lookups;

/**
 * @author spindizzy
 */
public class ProjectNode extends AbstractNode {

    private static final Logger LOG = Logger.getLogger(ProjectNode.class.getName());

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/folder.png";

    private final Project project;
    
    private final PlanNodeFactory planNodeFactory;

    public ProjectNode(final Project project) {
        super(Children.LEAF, Lookups.singleton(project));
        this.project = project;
        this.planNodeFactory = new PlanNodeFactory(project);

        init();
    }

    private void init() {
        setName(project.getKey());
        setDisplayName(project.getName());
        setShortDescription(project.getName());
        setIconBaseWithExtension(ICON_BASE);
        
        setChildren(Children.create(planNodeFactory, true));
    }
   

    @Override
    public Action[] getActions(final boolean context) {
        List<Action> actions = new ArrayList<>();

        actions.add(OpenUrlAction.newAction(project));
        return actions.toArray(new Action[actions.size()]);
    }

}
