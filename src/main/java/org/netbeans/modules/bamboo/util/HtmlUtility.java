package org.netbeans.modules.bamboo.util;

/**
 * This class provides methods to handle string with HTML markup.
 * @author spindizzy
 */
public class HtmlUtility {
    
    /**
     * This methods returns only the HTML content from the given text.
     * If there is not HTML in the text, the result will be an empty string.
     * @param text string with possible HTML content.
     * @return HTML content or empty string.
     */
    public String extractHtmlContent(String text) {
        return "";
    }
    
    /**
     * Returns the normal text, which is not embedded within HTML.
     * @param text string with possible HTML content.
     * @return normal text or empty string.
     */
    public String extractNormalText(String text){
        return "";
    }
}
