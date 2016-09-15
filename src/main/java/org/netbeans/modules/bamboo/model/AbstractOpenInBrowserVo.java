package org.netbeans.modules.bamboo.model;

import lombok.Setter;
import org.netbeans.modules.bamboo.glue.OpenableInBrowser;
import static java.lang.String.format;

/**
 *
 * @author spindizzy
 */
public abstract class AbstractOpenInBrowserVo extends AbstractVo implements OpenableInBrowser {

    private transient String serverUrl;

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    
    @Override
    public String getUrl() {
        return format(OpenableInBrowser.BROWSE, serverUrl, getKey());
    }
}
