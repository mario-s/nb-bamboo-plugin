package org.netbeans.modules.bamboo.glue;

import lombok.Data;
import org.netbeans.modules.bamboo.rest.model.State;
import static java.lang.String.format;


/**
 * This class represent a Bamboo build project.
 *
 * @author spindizzy
 */
@Data
public class BuildProject implements OpenableInBrowser{
    
    private static final String BROWSE = "%s/browse/%s";
    private String serverUrl;
    private String key;
    private String name;
    private String shortName;
    private transient boolean enabled;
    private transient State state = State.Unknown;

    @Override
    public String getUrl() {
        return format(BROWSE, serverUrl, key);
    }
}
