package org.netbeans.modules.bamboo.model.rest;

import lombok.Data;

/**
 *
 * @author spindizzy
 */
@Data
public class Change {
    private String changesetId;
    private String author;
    private String userName;
    private String fullName;
    
    private String comment;
    private String commitUrl;
    
    private String date;
    
    private Files files;
}
