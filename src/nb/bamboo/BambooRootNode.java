package nb.bamboo;

import java.util.List;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import static nb.bamboo.Bundle.*;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.NbBundle.Messages;

@ServicesTabNodeRegistration(
        name = BambooRootNode.BAMBOO_NODE_NAME,
        displayName = "#LBL_BambooNode",
        shortDescription = "#TIP_BambooNode",
        iconResource = BambooRootNode.ICON_BASE,
        position = 600)
@Messages({
  "LBL_BambooNode=Bamboo Builders",
  "TIP_BambooNode=Bamboo continuous integration servers."
})
public class BambooRootNode extends AbstractNode {

  static final String BAMBOO_NODE_NAME = "bamboo";

  static final String ICON_BASE = "nb/bamboo/resources/bamboo.png";

  private BambooRootNode() {
    super(Children.create(new RootNodeChildren(), true));
    setName(BAMBOO_NODE_NAME);
    setDisplayName(LBL_BambooNode());
    setShortDescription(TIP_BambooNode());
    setIconBaseWithExtension(ICON_BASE);
  }

  private static class RootNodeChildren extends ChildFactory {

    public RootNodeChildren() {
    }

    @Override
    protected Node createNodeForKey(final Object key) {
      return null;
    }

    @Override
    protected boolean createKeys(final List toPopulate) {
      return true;
    }
  }
}
