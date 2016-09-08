package org.netbeans.modules.bamboo.model;

import static java.lang.String.format;
import lombok.Setter;
import org.netbeans.modules.bamboo.glue.OpenableInBrowser;

/**
 *
 * @author spindizzy
 */
@Setter
public abstract class AbstractOpenInBrowserVo implements OpenableInBrowser {

    private String serverUrl;

    public abstract String getKey();

    @Override
    public String getUrl() {
        return format(OpenableInBrowser.BROWSE, serverUrl, getKey());
    }
}
