package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;
import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import org.netbeans.modules.bamboo.model.rest.JiraIssues;

/**
 *
 * @author spindizzy
 */
public class IssuesVoConverter extends AbstractVoConverter<JiraIssues, Collection<IssueVo>> {

    @Override
    public Collection<IssueVo> convert(JiraIssues src) {
        return convert(src, new IssueVoConverter());
    }

}
