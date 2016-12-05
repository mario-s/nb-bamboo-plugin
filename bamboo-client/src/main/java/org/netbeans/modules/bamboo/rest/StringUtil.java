package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class provides utility methods to deal with strings.
 * @author spindizzy
 */
final class StringUtil {
    
    private StringUtil() {
        
    }

    /**
     * Splits the given string into a collection.
     * @param str the string to split
     * @return a collection
     */
    static Collection<String> split(final String str) {
        if ((str != null) && (str.trim().length() > 0)) {
            String[] escaped = str.split("(?<!/)/(?!/)");
            List<String> list = new ArrayList<>(escaped.length);

            for (String e : escaped) {
                list.add(e.replace("//", "/"));
            }

            return list;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * This method joins the pieces into a new string.
     * @param pieces the collection to join
     * @return a new string
     */
    static String join(final Collection<String> pieces) {
        StringBuilder b = new StringBuilder();

        for (String piece : pieces) {
            assert !piece.startsWith("/") && !piece.endsWith("/") : piece;

            String escaped = piece.replace("/", "//");

            if (b.length() > 0) {
                b.append('/');
            }

            b.append(escaped);
        }

        return b.toString();
    }
}
