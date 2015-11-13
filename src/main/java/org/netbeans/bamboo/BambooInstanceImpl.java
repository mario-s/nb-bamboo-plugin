package org.netbeans.bamboo;

@Deprecated
final class BambooInstanceImpl implements BambooInstanceable {

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
