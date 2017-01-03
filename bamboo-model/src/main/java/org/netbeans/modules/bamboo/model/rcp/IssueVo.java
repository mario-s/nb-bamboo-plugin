package org.netbeans.modules.bamboo.model.rcp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author spindizzy
 */
@Data
@EqualsAndHashCode(of = "key")
@NoArgsConstructor
public class IssueVo {

    private String key;
    private String summary;
    private String assignee;
    private String iconUrl;
    private String link;

    public IssueVo(String key) {
        this.key = key;
    }
}
