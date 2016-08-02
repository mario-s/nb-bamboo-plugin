package org.netbeans.modules.bamboo.ui.nodes;

import org.netbeans.api.annotations.common.StaticResource;

import org.netbeans.modules.bamboo.glue.BuildProject;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

import org.openide.util.ImageUtilities;

import java.awt.Image;


/**
 * @author spindizzy
 */
public class ProjectNode extends AbstractNode {
    @StaticResource
    private static final String ICON_BASE = "org/netbeans/modules/bamboo/resources/grey.png";
    @StaticResource
    private static final String ICON_ENABLED = "org/netbeans/modules/bamboo/resources/blue.png";

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
            icon = ImageUtilities.loadImage(ICON_ENABLED);
        }

        return icon;
    }
}
