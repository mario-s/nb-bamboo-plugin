package org.netbeans.modules.bamboo.model.rcp;

import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static java.util.Collections.emptyList;

/**
 *
 * @author Mario Schroeder
 */
@Data
@EqualsAndHashCode(of = "changesetId")
public class ChangeVo {

    private String changesetId;

    private String author;

    private String userName;

    private String fullName;

    private String comment;

    private String commitUrl;

    private LocalDateTime date;

    private Collection<FileVo> files;
    
    public Collection<FileVo> getFiles() {
        return (files != null) ? files : emptyList();
    }
}
