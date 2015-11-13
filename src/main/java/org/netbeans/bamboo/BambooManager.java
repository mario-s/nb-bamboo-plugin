package org.netbeans.bamboo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BambooManager {

  private final List<BambooChangeListener> listeners = new ArrayList<BambooChangeListener>();

  private BambooManager() {
  }

  public static void addInstance(final String name, final String url) {
    BambooInstancesHolder.getDefault().addInstance(name, url);
  }

  public static void removeInstance(final String url) {
    BambooInstancesHolder.getDefault().removeInstance(url);
  }
  
  public static Collection<BambooInstance> getInstances() {
    return BambooInstancesHolder.getDefault().getInstances();
  }
  
  public static void addChangeListener(final BambooChangeListener listener) {
    BambooInstancesHolder.getDefault().addChangeListener(listener);
  }

}
