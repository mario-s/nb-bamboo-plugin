package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.Link;

/**
 *
 * @author Mario Schroeder
 */
public class IssueVoConverter extends AbstractVoConverter<Issue, IssueVo> {

    @Override
    public IssueVo convert(Issue src) {
        IssueVo target = new IssueVo(src.getKey());

        target.setAssignee(src.getAssignee());
        target.setSummary(src.getSummary());
        target.setIconUrl(src.getIconUrl());

        Link link = src.getLink();
        if (link != null) {
            target.setLink(link.getHref());
        }

        return target;
    }

}
