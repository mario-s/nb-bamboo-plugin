package org.netbeans.modules.bamboo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * This class provides methods to handle strings with HTML markup.
 *
 * @author spindizzy
 */
public class TextExtractUtility {

    private static final int ZERO = 0;

    private static final String QUOTE = "\"";
    private static final String CLS = ">";
    private static final String OPN = "<";
    
    private static final String LINK_REGEX = OPN +"a\\s.+<\\/a" + CLS;
    private static final String TEXT_REGEX = CLS + ".+" + OPN;
    private static final String URL_REGEX = "(http|https):\\/\\/.+" + QUOTE;

    /**
     * This methods returns the link content from the given text. If there is no link in the text, the result will be an
     * empty string.
     *
     * The string <code>test <a href="http://localhost">test</a></code> will result in
     * <code><a href="http://localhost">test</a></code>
     *
     * @param text string with a possible link
     * @return HTML link content or empty string.
     */
    public String extractLink(String text) {
        return find(text, LINK_REGEX);
    }

    /**
     * Returns the normal text, which is embedded within HTML tags.
     *
     * The string <code><a href="http://localhost">test</a></code> will result in <code>test</code>
     *
     * @param text string with possible HTML content.
     * @return normal text or empty string.
     */
    public String extractNormalText(String text) {
        String found = find(text, TEXT_REGEX);
        return removeFirst(removeLast(found, OPN), CLS);
    }
    

    /**
     * This methods returns the url from the given text. If there is no url in the text, the result will be an empty
     * string.
     *
     * The string <code><a href="http://localhost">test</a>s/code> will result in <code>http://localhost</code>
     *
     * @param text string with a possible url
     * @return url as string or empty string.
     */
    public String extractUrl(String text) {
        return removeLastQuote(find(text, URL_REGEX));
    }

    private String find(String text, String regex) {
        String result = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(text)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                result = matcher.group(ZERO);
            }
        }
        return result;
    }

    private String removeLastQuote(String text) {
        return removeLast(text, QUOTE);
    }
    
    private String removeLast(String text, String remove) {
        String result = StringUtils.EMPTY;
        if (!text.isEmpty()) {
            int pos = text.lastIndexOf(remove);
            result = (pos > ZERO) ? text.substring(ZERO, pos) : text;
        }
        return result;
    }
    
    private String removeFirst(String text, String remove) {
        String result = StringUtils.EMPTY;
        if (!text.isEmpty()) {
            int pos = text.indexOf(remove);
            int last = text.length();
            result = (pos >= ZERO) ? text.substring(pos + 1, last) : text;
        }
        return result;
    }
}
