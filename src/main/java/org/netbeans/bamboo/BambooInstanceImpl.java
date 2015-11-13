package org.netbeans.bamboo;

final class BambooInstanceImpl implements BambooInstance {

  private final String name;
  
  private final String url;
          
  BambooInstanceImpl(final String name, final String url) {
    this.name = name;
    this.url = url;
  }
  
  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getUrl() {
    return url;
  }
  
}
