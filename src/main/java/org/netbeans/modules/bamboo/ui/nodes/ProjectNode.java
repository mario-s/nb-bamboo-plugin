package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.api.annotations.common.StaticResource;

import org.netbeans.modules.bamboo.glue.BuildProject;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

import org.openide.util.ImageUtilities;

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.modules.bamboo.rest.model.State;
import org.netbeans.modules.bamboo.ui.actions.OpenUrlAction;


/**
 * @author spindizzy
 */
public class ProjectNode extends AbstractNode {
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/grey.png";
    @StaticResource
    private static final String ICON_ENABLED = "org/netbeans/modules/bamboo/resources/blue.png";
    @StaticResource
    private static final String ICON_FAILED = "org/netbeans/modules/bamboo/resources/red.png";

    private final BuildProject project;

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
    }

    @Override
    public Image getIcon(final int type) {
        Image icon = super.getIcon(type);

        if (project.isEnabled()) {
            
            if(project.getState().equals(State.Failed)){
                icon = ImageUtilities.loadImage(ICON_FAILED);
            }else{
                icon = ImageUtilities.loadImage(ICON_ENABLED);
            }
        }

        return icon;
    }
    
      @Override
    public Action[] getActions(final boolean context) {

        return new Action[]{OpenUrlAction.newAction(project)};
    }
}
