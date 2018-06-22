/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.netbeans.modules.bamboo.model.rcp.ModelChangedValues;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.netbeans.modules.bamboo.ui.actions.ActionConstants;
import org.netbeans.modules.bamboo.ui.util.DateFormatter;

import org.openide.actions.PropertiesAction;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.Lookups;
import org.openide.xml.XMLUtil;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.*;

/**
 * The UI for a {@link PlanVo}.
 *
 * @author Mario Schroeder
 */
@Log
@NbBundle.Messages({
    "TXT_Plan_Prop_Key=Plan Key",
    "DESC_Plan_Prop_Key=The key of the plan",
    "TXT_Plan_Prop_DisplayName=Display Name",
    "DESC_Plan_Prop_DisplayName=The display name of the plan, as shown as in the tree",
    "TXT_Plan_Prop_Name=Plan Name",
    "DESC_Plan_Prop_Name=The name of the build plan.",
    "TXT_Plan_Prop_Result_Number=Result Number",
    "DESC_Plan_Prop_Result_Number=Number of the last result for this plan.",
    "TXT_Plan_Prop_Result_Reason=Build Reason",
    "DESC_Plan_Prop_Result_Reason=The reason why this plan was built.",
    "TXT_Plan_Not_Watched=not watched",
    "TXT_Plan_Prop_Watched=Watched",
    "DESC_Plan_Prop_Watched=Whether you wish to be notified of failures in this plan.",
    "TXT_Plan_Prop_Reason_StartedTime=Build Started Time",
    "DESC_Plan_Prop_Reason_StartedTime=When the build for the last plan started.",
    "TXT_Plan_Prop_Reason_CompletedTime=Build Completed Time",
    "DESC_Plan_Prop_Reason_CompletedTime=When the build for the last plan completed.",
    "TXT_Plan_Prop_Reason_Duration=Build Duration",
    "DESC_Plan_Prop_Reason_Duration=Duration in seconds for the last build."
})
public class PlanNode extends AbstractInstanceChildNode {

    private static final String KEY = "key";

    private static final String SHORT_NAME = "shortName";

    private static final String BUILD_REASON = "buildReason";

    private static final String RESULT_NUMBER = "resultNumber";

    private static final String NOTIFY = "notify";

    private static final String DURATION = "buildDurationInSeconds";

    private static final String STARTED_TIME = "buildStartedTime";

    private static final String COMPLETED_TIME = "buildCompletedTime";

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
        log.log(Level.INFO, "plan changed: {0}", plan);

        updateHtmlDisplayName();

        String propName = evt.getPropertyName();
        if (ModelChangedValues.Silent.toString().equals(propName)) {
            plan.getParent().ifPresent(project -> {
                project.firePropertyChange(propName, null, plan);
            });
        } else {
            fireIconChange();
            firePropertySetsChange(null, getPropertySets());
        }
    }

    private void updateHtmlDisplayName() {
        try {
            String oldDisplayName = getHtmlDisplayName();

            String escapedName = XMLUtil.toElementContent(plan.getShortName());
            StringBuilder builder = new StringBuilder(escapedName);

            builder.append(lifeCycleStateToString());
            builder.append(stateToString());
            builder.append(watching());

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
        StringBuilder builder = new StringBuilder(SPACE);
        builder.append(toGray(getResult().getState().name()));
        return builder.toString();
    }

    private String watching() {
        String res = StringUtils.EMPTY;
        if (!plan.isNotify()) {
            StringBuilder builder = new StringBuilder(SPACE);
            builder.append(toGray(TXT_Plan_Not_Watched()));
            res = builder.toString();
        }
        return res;
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

        actions.addAll(findActions(ActionConstants.COMMON_ACTION_PATH));
        actions.add(null);
        actions.addAll(findActions(ActionConstants.PLAN_ACTION_PATH));
        actions.add(null);
        actions.add(SystemAction.get(PropertiesAction.class));

        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    protected Sheet createSheet() {
        Sheet.Set set = Sheet.createPropertiesSet();
        String shortName = plan.getShortName();
        set.setDisplayName(shortName);

        set.put(new StringReadPropertySupport(SHORT_NAME, TXT_Plan_Prop_DisplayName(), DESC_Plan_Prop_DisplayName()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return shortName;
            }
        });

        set.put(new StringReadPropertySupport(KEY, TXT_Plan_Prop_Key(), DESC_Plan_Prop_Key()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return plan.getKey();
            }
        });

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

        set.put(new StringReadPropertySupport(STARTED_TIME, TXT_Plan_Prop_Reason_StartedTime(),
                DESC_Plan_Prop_Reason_StartedTime()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return DateFormatter.format(getResult().getBuildStartedTime());
            }

        });

        set.put(new StringReadPropertySupport(COMPLETED_TIME, TXT_Plan_Prop_Reason_CompletedTime(),
                DESC_Plan_Prop_Reason_CompletedTime()) {
            @Override
            public String getValue() throws IllegalAccessException, InvocationTargetException {
                return DateFormatter.format(getResult().getBuildCompletedTime());
            }

        });

        set.put(new LongReadPropertySupport(DURATION, TXT_Plan_Prop_Reason_Duration(), DESC_Plan_Prop_Reason_Duration()) {
            @Override
            public Long getValue() throws IllegalAccessException, InvocationTargetException {
                return getResult().getBuildDurationInSeconds();
            }

        });

        set.put(new BooleanReadWritePropertySupport(NOTIFY, TXT_Plan_Prop_Watched(), DESC_Plan_Prop_Watched()) {
            @Override
            public Boolean getValue() throws IllegalAccessException, InvocationTargetException {
                return plan.isNotify();
            }

            @Override
            public void setValue(Boolean val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                plan.setNotify(val);
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
