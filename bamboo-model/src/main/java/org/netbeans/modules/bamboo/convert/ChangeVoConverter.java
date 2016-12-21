package org.netbeans.modules.bamboo.convert;

import org.netbeans.modules.bamboo.model.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Change;

/**
 *
 * @author spindizzy
 */
class ChangeVoConverter implements VoConverter<Change, ChangeVo> {
    
    @Override
    public ChangeVo convert(Change src) {
        ChangeVo target = new ChangeVo();
        target.setChangesetId(src.getChangesetId());
        target.setAuthor(src.getAuthor());
        target.setFullName(src.getFullName());
        target.setUserName(src.getUserName());
        target.setComment(src.getComment());
        target.setCommitUrl(src.getCommitUrl());
        LocalDateTimeConverter dateConverter = new LocalDateTimeConverter();
        dateConverter.convert(src.getDate()).ifPresent((date) -> target.setDate(date));
        //TODO convert files
        return target;
    }
    
}
