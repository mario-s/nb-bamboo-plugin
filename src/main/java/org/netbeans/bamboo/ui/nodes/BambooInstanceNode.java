package org.netbeans.bamboo.ui.nodes;

import org.netbeans.bamboo.BambooInstance;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

public class BambooInstanceNode extends AbstractNode {

  public BambooInstanceNode(final BambooInstance instance) {
    super(Children.LEAF);
  }

  
  
}
