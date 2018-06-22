/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.client.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class provides utility methods to deal with strings.
 * @author Mario Schroeder
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
