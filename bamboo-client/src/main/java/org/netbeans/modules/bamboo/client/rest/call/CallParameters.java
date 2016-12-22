package org.netbeans.modules.bamboo.client.rest.call;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.netbeans.modules.bamboo.model.InstanceValues;

import static java.util.Collections.emptyMap;

/**
 * This class keeps the necessary values for an Api call together.
 * @author spindizzy
 */
@Getter
@RequiredArgsConstructor
final class CallParameters<T> {
    private final Class<T> responseClass;
    private final InstanceValues values;
    @Setter
    private boolean json;
    @Setter
    private String path = "";
    @Setter
    private Map<String, String> parameters =  emptyMap();     
    
}
