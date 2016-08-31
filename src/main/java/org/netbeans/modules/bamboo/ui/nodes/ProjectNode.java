package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.api.annotations.common.StaticResource;

import org.netbeans.modules.bamboo.glue.BuildProject;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

import org.openide.util.ImageUtilities;

import java.awt.Image;
import java.io.CharConversionException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.modules.bamboo.glue.SharedConstants;
import org.netbeans.modules.bamboo.rest.model.State;
import org.netbeans.modules.bamboo.ui.actions.OpenUrlAction;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Project_Prop_Name;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.DESC_Project_Prop_Number;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Project_Prop_Name;
import static org.netbeans.modules.bamboo.ui.nodes.Bundle.TXT_Project_Prop_Number;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;
import org.openide.xml.XMLUtil;

/**
 * @author spindizzy
 */
public class ProjectNode extends AbstractNode {
    
    private static final String RESULT_NUMBER = "resultNumber";
    private static final String STYLE = "<font color='!controlShadow'>(%s)</font>";
    private static final String SPC = " ";

    private static final Logger LOG = Logger.getLogger(ProjectNode.class.getName());

    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/grey.png";
    @StaticResource
    private static final String ICON_ENABLED = "org/netbeans/modules/bamboo/resources/blue.png";
    @StaticResource
    private static final String ICON_FAILED = "org/netbeans/modules/bamboo/resources/red.png";

    private final BuildProject project;

    private String htmlDisplayName;
    
    private Sheet.Set sheetSet;

    public ProjectNode(final BuildProject project) {
        super(Children.LEAF);
        this.project = project;

        init();
    }

    private void init() {
        setName(project.getName());
        setDisplayName(project.getShortName());
        setShortDescription(project.getName());
        setIconBaseWithExtension(ICON_BASE);
        updateHtmlDisplayName();
    }

    private void updateHtmlDisplayName() {
        try {
            String oldDisplayName = getHtmlDisplayName();

            String escapedName = XMLUtil.toElementContent(project.getShortName());
            StringBuilder builder = new StringBuilder(escapedName);

            builder.append(SPC).append(toGray(project.getLifeCycleState().name()));
            builder.append(SPC).append(toGray(project.getState().name()));
            
            htmlDisplayName = builder.toString();

            fireDisplayNameChange(oldDisplayName, htmlDisplayName);
        } catch (CharConversionException ex) {
            LOG.log(Level.FINE, ex.getMessage(), ex);
        }
    }

    @Override
    public String getHtmlDisplayName() {
        return htmlDisplayName;
    }

    @Override
    public Image getIcon(final int type) {
        Image icon = super.getIcon(type);

        if (project.isEnabled()) {

            if (project.getState().equals(State.Failed)) {
                icon = ImageUtilities.loadImage(ICON_FAILED);
            } else {
                icon = ImageUtilities.loadImage(ICON_ENABLED);
            }
        }

        return icon;
    }

    @Override
    public Action[] getActions(final boolean context) {

        return new Action[]{OpenUrlAction.newAction(project)};
    }

    private String toGray(String text) {
        return String.format(STYLE, text);
    }
    
    @Override
    public PropertySet[] getPropertySets() {
        return new PropertySet[]{getSheetSet()};
    }

    @NbBundle.Messages({
        "TXT_Project_Prop_Name=Project Name",
        "DESC_Project_Prop_Name=Number of the last result for this build",
        "TXT_Project_Prop_Number=Result Number",
        "DESC_Project_Prop_Number=Number of the last result for this build"
    })
    private PropertySet getSheetSet() {
        if (sheetSet == null) {
            sheetSet = Sheet.createPropertiesSet();
            sheetSet.setDisplayName(project.getShortName());
            
            sheetSet.put(new StringReadPropertySupport(SharedConstants.PROP_NAME, TXT_Project_Prop_Name(), DESC_Project_Prop_Name()) {
                @Override
                public String getValue() throws IllegalAccessException, InvocationTargetException {
                    return project.getName();
                }
            });

            sheetSet.put(new IntReadPropertySupport(RESULT_NUMBER, TXT_Project_Prop_Number(), DESC_Project_Prop_Number()) {
                @Override
                public Integer getValue() throws IllegalAccessException, InvocationTargetException {
                    return project.getResultNumber();
                }
            });

        }
        return sheetSet;
    }


}
