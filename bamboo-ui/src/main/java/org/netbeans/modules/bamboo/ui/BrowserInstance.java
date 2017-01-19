package org.netbeans.modules.bamboo.ui;

import java.net.MalformedURLException;
import java.net.URL;
import lombok.extern.java.Log;
import org.openide.awt.HtmlBrowser.URLDisplayer;

import static org.openide.awt.HtmlBrowser.URLDisplayer.getDefault;

/**
 * Singleton for {@link URLDisplayer}.
 * 
 * @author Mario Schroeder
 */
@Log
public enum BrowserInstance {
    Instance;
    
    private final URLDisplayer urlDisplayer;
    
    private BrowserInstance() {
        urlDisplayer = getDefault();
    }
    
    public void showURL(URL url) {
        urlDisplayer.showURL(url);
    }
    
    public void showURL(String url) {
        try {
            showURL(new URL(url));
        } catch (MalformedURLException ex) {
            log.warning(ex.getMessage());
        }
    }
}
