package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyEditor;
import java.io.CharConversionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.Action;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.netbeans.modules.bamboo.ui.actions.ActionConstants;
import org.netbeans.modules.bamboo.ui.actions.OpenUrlAction;

import org.openide.actions.PropertiesAction;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.xml.XMLUtil;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Plan_Prop_Name;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Plan_Prop_Result_Number;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Plan_Prop_Result_Reason;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Plan_Prop_Name;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Plan_Prop_Result_Number;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Plan_Prop_Result_Reason;

/**
 * The UI for a {@link PlanVo}.
 *
 * @author spindizzy
 */
@Log
@NbBundle.Messages({
    "TXT_Plan_Prop_Name=Plan Name",
    "DESC_Plan_Prop_Name=The name of the build plan",
    "TXT_Plan_Prop_Result_Number=Result Number",
    "DESC_Plan_Prop_Result_Number=Number of the last result for this plan",
    "TXT_Plan_Prop_Result_Reason=Build Reason",
    "DESC_Plan_Prop_Result_Reason=The reason why this plan was built"
})
public class PlanNode extends AbstractInstanceChildNode {

    private static final String BUILD_REASON = "buildReason";
    private static final String RESULT_NUMBER = "resultNumber";

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/grey.png";
    @StaticResource
    private static final String ICON_ENABLED = "org/netbeans/modules/bamboo/resources/blue.png";
    @StaticResource
    private static final String ICON_FAILED = "org/netbeans/modules/bamboo/resources/red.png";

    private static final String NO_CONTROL_SHADOW = "<font color='!controlShadow'>(%s)</font>";

    private final PlanVo plan;

    private final BuildReasonEditor buildReasonEditor;

    private String htmlDisplayName;

    public PlanNode(final PlanVo plan) {
        super(Lookups.singleton(plan));
        this.plan = plan;
        this.buildReasonEditor = new BuildReasonEditor();
        init();
    }

    private void init() {
        setName(plan.getName());
        setDisplayName(plan.getShortName());
        setShortDescription(plan.getName());
        setIconBaseWithExtension(ICON_BASE);
        updateHtmlDisplayName();

        plan.addPropertyChangeListener(this);
    }

    private String toGray(String text) {
        return String.format(NO_CONTROL_SHADOW, text);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (log.isLoggable(Level.INFO)) {
            log.info(String.format("plan changed: %s", plan));
        }
        updateHtmlDisplayName();
        fireIconChange();
        firePropertySetsChange(null, getPropertySets());
    }

    private void updateHtmlDisplayName() {
        try {
            String oldDisplayName = getHtmlDisplayName();

            String escapedName = XMLUtil.toElementContent(plan.getShortName());
            StringBuilder builder = new StringBuilder(escapedName);

            builder.append(lifeCycleStateToString());
            builder.append(stateToString());

            htmlDisplayName = builder.toString();

            fireDisplayNameChange(oldDisplayName, htmlDisplayName);
        } catch (CharConversionException ex) {
            log.log(Level.FINE, ex.getMessage(), ex);
        }
    }

    private String lifeCycleStateToString() {
        StringBuilder builder = new StringBuilder();
        LifeCycleState lifeCycleState = getResult().getLifeCycleState();
        //no need to notify about a finished lifecycle since there is no way to figure out running plans (yet)
        if (!LifeCycleState.Finished.equals(lifeCycleState)) {
            builder.append(SPACE).append(toGray(lifeCycleState.name()));
        }
        return builder.toString();
    }

    private String stateToString() {
        StringBuilder builder = new StringBuilder();
        builder.append(SPACE).append(toGray(getResult().getState().name()));
        return builder.toString();
    }

    private ResultVo getResult() {
        ResultVo result = plan.getResult();
        return (result != null) ? result : new ResultVo();
    }

    @Override
    public String getHtmlDisplayName() {
        return htmlDisplayName;
    }


    @Override
    public Image getIcon(final int type) {
        Image icon = super.getIcon(type);

        if (plan.isEnabled()) {

            if (getResult().getState().equals(State.Failed)) {
                icon = ImageUtilities.loadImage(ICON_FAILED);
            } else {
                icon = ImageUtilities.loadImage(ICON_ENABLED);
            }
        }

        return icon;
    }

    @Override
    public Action[] getActions(final boolean context) {
        List<Action> actions = new ArrayList<>();
        
        actions.add(OpenUrlAction.newAction(plan));
        actions.addAll(findActions(ActionConstants.PLAN_ACTION_PATH));
        actions.add(null);
        actions.add(SystemAction.get(PropertiesAction.class));
        
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName(plan.getShortName());

        set.put(new PropertySupport.Name(this, TXT_Plan_Prop_Name(), DESC_Plan_Prop_Name()));

        set.put(new IntReadPropertySupport(RESULT_NUMBER, TXT_Plan_Prop_Result_Number(), DESC_Plan_Prop_Result_Number()) {
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                return getResult().getNumber();
            }
        });

        set.put(new StringReadPropertySupport(BUILD_REASON, TXT_Plan_Prop_Result_Reason(),
                DESC_Plan_Prop_Result_Reason()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                String buildReason = getResult().getBuildReason();
                return (buildReason == null) ? StringUtils.EMPTY : buildReason;
            }

            @Override
            public PropertyEditor getPropertyEditor() {
                return buildReasonEditor;
            }
        });

        Sheet sheet = Sheet.createDefault();

        sheet.put(set);

        return sheet;
    }

    @Override
    public void destroy() throws IOException {
        super.destroy();
        plan.removePropertyChangeListener(this);
    }

}
