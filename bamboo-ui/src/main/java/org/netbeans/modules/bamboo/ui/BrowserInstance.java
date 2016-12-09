package org.netbeans.modules.bamboo.ui;

import java.net.URL;
import org.openide.awt.HtmlBrowser.URLDisplayer;

import static org.openide.awt.HtmlBrowser.URLDisplayer.getDefault;

/**
 * Singleton for {@link URLDisplayer}.
 * 
 * @author spindizzy
 */
public enum BrowserInstance {
    Instance;
    
    private final URLDisplayer urlDisplayer;
    
    private BrowserInstance() {
        urlDisplayer = getDefault();
    }
    
    public void showURL(URL url) {
        urlDisplayer.showURL(url);
    }
}
