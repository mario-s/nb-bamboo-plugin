package org.netbeans.modules.bamboo.model;

import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author spindizzy
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

    private String date;
    
    private Collection<FileVo> files;
}
