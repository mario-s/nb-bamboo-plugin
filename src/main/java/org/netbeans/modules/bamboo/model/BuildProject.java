package org.netbeans.modules.bamboo.model;

import static java.lang.String.format;
import java.util.Collection;
import lombok.Data;
import org.netbeans.modules.bamboo.glue.OpenableInBrowser;

/**
 * This class represent a Bamboo build project.
 *
 * @author spindizzy
 */
@Data
public class BuildProject implements OpenableInBrowser {

    private String name;
    private String key;
    private String serverUrl;

    private Collection<Plan> plans;

    @Override
    public String getUrl() {
        return format(BROWSE, serverUrl, key);
    }
}
