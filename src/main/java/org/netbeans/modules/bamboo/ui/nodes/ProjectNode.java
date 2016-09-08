package org.netbeans.modules.bamboo.ui.nodes;


import java.lang.reflect.InvocationTargetException;
import org.netbeans.modules.bamboo.model.rest.Project;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.model.rest.Plan;
import org.netbeans.modules.bamboo.ui.actions.OpenUrlAction;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Instance_Prop_Plans;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Instance_Prop_Plans;
import org.openide.actions.PropertiesAction;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;

/**
 * @author spindizzy
 */
public class ProjectNode extends AbstractNode {
    
    private static final String PLANS = "plans";

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
        actions.add(null);
        actions.add(SystemAction.get(PropertiesAction.class));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    @NbBundle.Messages({
        "TXT_Instance_Prop_Plans=Plans",
        "DESC_Instance_Prop_Plans=number of all available build plans",
    })
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName(project.getName());
        
        set.put(new IntReadPropertySupport(PLANS, TXT_Instance_Prop_Plans(), DESC_Instance_Prop_Plans()) {
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                final Collection<Plan> plans = project.plansAsCollection();
                return (plans != null) ? plans.size() : 0;
            }
        });

        sheet.put(set);
        
        return sheet;
    }
}
