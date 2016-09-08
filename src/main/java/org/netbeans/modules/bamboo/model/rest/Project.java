package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import static java.lang.String.format;
import java.util.Collection;
import java.util.Collections;
import lombok.Data;
import org.netbeans.modules.bamboo.glue.OpenableInBrowser;
import org.netbeans.modules.bamboo.model.AbstractChangeSupportEntity;

/**
 *
 * @author spindizzy
 */
@Data
@JsonRootName(value = "project")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends AbstractChangeSupportEntity implements OpenableInBrowser{
    private String key;
    private Link link;
    private String name;
    private transient Plans plans;
    
    public Collection<Plan> plansAsCollection() {
        return (plans == null) ? Collections.emptyList() : plans.getPlan();
    }
    
    @JsonIgnore
    private String serverUrl;
    
    @Override
    public String getUrl() {
        return format(OpenableInBrowser.BROWSE, serverUrl, key);
    }
}
