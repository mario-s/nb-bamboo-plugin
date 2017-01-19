package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Change;


/**
 *
 * @author Mario Schroeder
 */
public class ChangeVoConverter extends AbstractVoConverter<Change, ChangeVo> {
    
    @Override
    public ChangeVo convert(Change src) {
        ChangeVo target = new ChangeVo();
        
        copyProperties(src, target);
               
        toDate(src.getDate()).ifPresent((date) -> target.setDate(date));
        
        convertFiles(src, target);
        
        return target;
    }

    private void convertFiles(Change src, ChangeVo target) {
        CollectionVoConverter converter = new CollectionVoConverter(new FileVoConverter());
        target.setFiles(converter.convert(src.getFiles()));
    }
    
}
