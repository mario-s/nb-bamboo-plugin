package org.netbeans.modules.bamboo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import static java.lang.String.format;
import lombok.Data;
import org.netbeans.modules.bamboo.glue.OpenableInBrowser;

@Data
@JsonRootName(value = "plan")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan implements OpenableInBrowser {

    private String key;
    private Link link;
    private String name;
    private String shortKey;
    private String shortName;
    private transient PlanType type;
    private transient boolean enabled;
    private transient Result result;
    
    @JsonIgnore
    private String serverUrl;

    @Override
    public String getUrl() {
        return format(OpenableInBrowser.BROWSE, serverUrl, key);
    }
}
