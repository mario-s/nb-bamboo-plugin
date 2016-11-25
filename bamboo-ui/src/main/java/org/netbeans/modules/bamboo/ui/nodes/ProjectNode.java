package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.model.ModelChangedValues;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;
import org.netbeans.modules.bamboo.ui.actions.ActionConstants;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Instance_Prop_Plans;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Instance_Prop_Plans;

import org.openide.actions.PropertiesAction;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;

/**
 * The UI for a {@link ProjectVo}.
 *
 * @author spindizzy
 */
@NbBundle.Messages({
    "TXT_Instance_Prop_Plans=Plans",
    "DESC_Instance_Prop_Plans=number of all available build plans"})
public class ProjectNode extends AbstractInstanceChildNode {

    @StaticResource
    private static final String FOLDER = "org/netbeans/modules/bamboo/resources/folder.png";

    private final ProjectVo project;

    public ProjectNode(final ProjectVo project) {
        super(new PlanNodeFactory(project), Lookups.singleton(project));
        this.project = project;
        init();
    }

    private void init() {
        setName(project.getKey());
        setDisplayName(project.getName());
        setShortDescription(project.getName());
        setIconBaseWithExtension(FOLDER);

        project.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refreshChildren();
    }

    @Override
    public Action[] getActions(final boolean context) {
        List<Action> actions = new ArrayList<>();

        actions.addAll(findActions(ActionConstants.COMMON_ACTION_PATH));
        actions.add(null);
        actions.add(SystemAction.get(PropertiesAction.class));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    protected Sheet createSheet() {

        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName(project.getName());

        set.put(new IntReadPropertySupport(ModelChangedValues.Plans.toString(), TXT_Instance_Prop_Plans(),
                DESC_Instance_Prop_Plans()) {
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                final Collection<PlanVo> plans = project.getChildren();
                return (plans != null) ? plans.size() : 0;
            }
        });

        Sheet sheet = Sheet.createDefault();
        sheet.put(set);

        return sheet;
    }

    @Override
    public void destroy() throws IOException {
        project.removePropertyChangeListener(this);
        super.destroy();
    }

}
