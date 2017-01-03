package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import org.netbeans.modules.bamboo.model.rest.JiraIssues;

/**
 *
 * @author spindizzy
 */
public class IssuesVoConverter implements VoConverter<JiraIssues, Collection<IssueVo>>{

    @Override
    public Collection<IssueVo> convert(JiraIssues src) {
        if (src != null) {
            IssueVoConverter converter = new IssueVoConverter();
            return src.asCollection().stream().map(
                    c -> {
                        return converter.convert(c);
                    }).collect(toList());
        }
        return emptyList();
    }
    
}
