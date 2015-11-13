package org.netbeans.bamboo.manager;

import org.netbeans.bamboo.impl.BambooManagerImpl;
import org.netbeans.bamboo.BambooChangeListener;
import org.netbeans.bamboo.BambooInstance;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BambooManager {

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
