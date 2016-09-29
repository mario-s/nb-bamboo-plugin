package org.netbeans.modules.bamboo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * This class provides methods to handle string with HTML markup.
 *
 * @author spindizzy
 */
public class HtmlUtility {
    private static final int FIRST = 0;

    private static final String LINK_REGEX = "<a.+<\\/a>";

    /**
     * This methods returns only the HTML content from the given text. If there is not HTML in the text, the result will
     * be an empty string.
     *
     * @param text string with possible HTML content.
     * @return HTML content or empty string.
     */
    public String extractLink(String text) {
        return find(text, LINK_REGEX);
    }
    

    /**
     * Returns the normal text, which is not embedded within HTML.
     *
     * @param text string with possible HTML content.
     * @return normal text or empty string.
     */
    public String extractNormalText(String text) {
        return "";
    }
    
    private String find(String text, String regex){
        String result = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(text)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()){
                result = matcher.group(FIRST);
            }
        }
        return result;
    }
}
