package org.netbeans.modules.bamboo.model;

import lombok.Setter;
import org.netbeans.modules.bamboo.glue.OpenableInBrowser;
import static java.lang.String.format;

/**
 *
 * @author spindizzy
 */
@Setter
public abstract class AbstractOpenInBrowserVo extends AbstractVo implements OpenableInBrowser {

    private transient String serverUrl;

    @Override
    public String getUrl() {
        return format(OpenableInBrowser.BROWSE, serverUrl, getKey());
    }
}
