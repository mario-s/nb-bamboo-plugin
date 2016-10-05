package org.netbeans.modules.bamboo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.netbeans.modules.bamboo.glue.TextExtractable;
import org.openide.util.lookup.ServiceProvider;

/**
 * This class provides methods to handle strings with HTML markup.
 *
 * @author spindizzy
 */
@ServiceProvider(service = TextExtractable.class)
public class TextExtractor implements TextExtractable{

    private static final int ZERO = 0;

    private static final String QUOTE = "\"";
    private static final String CLS = ">";
    private static final String OPN = "<";
    
    private static final String LINK_REGEX = OPN +"a\\s.+<\\/a" + CLS;
    private static final String TEXT_REGEX = CLS + ".+" + OPN;
    private static final String URL_REGEX = "(http|https):\\/\\/.+" + QUOTE;

    @Override
    public String extractLink(String text) {
        return find(text, LINK_REGEX);
    }

    @Override
    public String extractNormalText(String text) {
        String found = find(text, TEXT_REGEX);
        return removeFirst(removeLast(found, OPN), CLS);
    }
    
    
    @Override
    public String substring(String complete, String toRemove) {
        int pos = complete.indexOf(toRemove);
        return complete.substring(ZERO, pos);
    }


    @Override
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
