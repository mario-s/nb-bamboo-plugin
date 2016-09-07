package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Image;
import java.io.CharConversionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.bamboo.glue.SharedConstants;
import org.netbeans.modules.bamboo.model.Plan;
import org.netbeans.modules.bamboo.model.Result;
import org.netbeans.modules.bamboo.model.State;
import org.netbeans.modules.bamboo.ui.actions.OpenUrlAction;

import org.openide.actions.PropertiesAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.xml.XMLUtil;

import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Plan_Prop_Name;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Plan_Prop_Result_Number;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Plan_Prop_Name;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Plan_Prop_Result_Number;

/**
 *
 * @author spindizzy
 */
public class PlanNode extends AbstractNode {
    
    private static final String RESULT_NUMBER = "resultNumber";
    private static final String STYLE = "<font color='!controlShadow'>(%s)</font>";
    private static final String SPC = " ";

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/grey.png";
    @StaticResource
    private static final String ICON_ENABLED = "org/netbeans/modules/bamboo/resources/blue.png";
    @StaticResource
    private static final String ICON_FAILED = "org/netbeans/modules/bamboo/resources/red.png";

    private static final Logger LOG = Logger.getLogger(PlanNode.class.getName());

    private String htmlDisplayName;

    private final Plan plan;

    public PlanNode(final Plan plan) {
        super(Children.LEAF, Lookups.singleton(plan));
        this.plan = plan;

        init();
    }

    private void init() {
        setName(plan.getName());
        setDisplayName(plan.getShortName());
        setShortDescription(plan.getName());
        setIconBaseWithExtension(ICON_BASE);
        updateHtmlDisplayName();
    }

    private void updateHtmlDisplayName() {
        try {
            String oldDisplayName = getHtmlDisplayName();

            String escapedName = XMLUtil.toElementContent(plan.getShortName());
            StringBuilder builder = new StringBuilder(escapedName);

            builder.append(SPC).append(toGray(getResult().getLifeCycleState().name()));
            builder.append(SPC).append(toGray(getResult().getState().name()));

            htmlDisplayName = builder.toString();

            fireDisplayNameChange(oldDisplayName, htmlDisplayName);
        } catch (CharConversionException ex) {
            LOG.log(Level.FINE, ex.getMessage(), ex);
        }
    }

    private Result getResult() {
        Result result = plan.getResult();
        return (result != null) ? result : new Result();
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

    private String toGray(String text) {
        return String.format(STYLE, text);
    }

    @Override
    public Action[] getActions(final boolean context) {
        List<Action> actions = new ArrayList<>();

        actions.add(OpenUrlAction.newAction(plan));
        actions.add(null);
        actions.add(SystemAction.get(PropertiesAction.class));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    @NbBundle.Messages({
        "TXT_Plan_Prop_Name=Plan Name",
        "DESC_Plan_Prop_Name=The name of the build plan",
        "TXT_Plan_Prop_Result_Number=Result Number",
        "DESC_Plan_Prop_Result_Number=Number of the last result for this plan"
    })
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setDisplayName(plan.getShortName());

        set.put(new StringReadPropertySupport(SharedConstants.PROP_NAME, TXT_Plan_Prop_Name(), DESC_Plan_Prop_Name()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return plan.getName();
            }
        });

        set.put(new IntReadPropertySupport(RESULT_NUMBER, TXT_Plan_Prop_Result_Number(), DESC_Plan_Prop_Result_Number()) {
            @Override
            public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                return getResult().getNumber();
            }
        });

        sheet.put(set);

        return sheet;
    }
}
