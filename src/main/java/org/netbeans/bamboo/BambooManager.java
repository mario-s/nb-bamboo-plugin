package org.netbeans.bamboo;

import java.util.Collection;

@Deprecated
public class BambooManager {

  private BambooManager() {
  }

  public static void addInstance(final String name, final String url) {
    BambooInstancesHolder.getDefault().addInstance(name, url);
  }

  public static void removeInstance(final String url) {
    BambooInstancesHolder.getDefault().removeInstance(url);
  }
  
  public static Collection<BambooInstanceable> getInstances() {
    return BambooInstancesHolder.getDefault().getInstances();
  }
  
  public static void addChangeListener(final BambooChangeListener listener) {
    BambooInstancesHolder.getDefault().addChangeListener(listener);
  }

}
