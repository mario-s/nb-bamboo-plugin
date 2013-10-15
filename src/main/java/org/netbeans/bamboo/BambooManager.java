package org.netbeans.bamboo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.netbeans.bamboo.impl.BambooManagerImpl;

public class BambooManager {

  private static BambooManager INSTANCE;

  private final List<BambooChangeListener> listeners = new ArrayList<BambooChangeListener>();

  private BambooManager() {
  }

  public static void addInstance(final String name, final String url) {
    BambooManagerImpl.getDefault().addInstance(name, url);
  }

  public static void removeInstance(final String url) {
    BambooManagerImpl.getDefault().removeInstance(url);
  }
  
  public static Collection<BambooInstance> getInstances() {
    return BambooManagerImpl.getDefault().getInstances();
  }
  
  public static void addChangeListener(final BambooChangeListener listener) {
    BambooManagerImpl.getDefault().addChangeListener(listener);
  }

}
