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
package org.netbeans.modules.bamboo.ui.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import org.jsoup.nodes.Element;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.jsoup.Jsoup.parse;

/**
 * This class provides methods to handle strings with HTML markup.
 *
 * @author Mario Schroeder
 */
public class TextExtractor {

    private static final String A_HREF = "a[href]";

    /**
     * This method returns true if the text contains a link otherwhise false.
     *
     * @param text the text which may contain a HTML link
     * @return true if the text contains a link otherwhise false.
     */
    public boolean containsLink(String text) {
        return !extractLinks(text).isEmpty();
    }

    /**
     * Removes all HTML tags from the given text.
     *
     * @param text text with possible HTML tags
     * @return text without tags
     */
    public String removeTags(String text) {
        return parse(text).text();
    }

    /**
     * This method replaces all apearances of links with the inner text.
     *
     * @param input text with possible links
     * @return text with out tags
     */
    public String replaceLinksWithText(String input) {
        String txt = input;
        if (containsLink(txt)) {
            Map<String, String> linkToText = linksAsStream(input).collect(toMap(Element::outerHtml, Element::text));

            for (Entry<String, String> entry : linkToText.entrySet()) {
                txt = txt.replaceFirst(entry.getKey(), entry.getValue());
            }
        }
        return txt;
    }

    /**
     * Extract all links from the text and returns them as a list of HTML tags. If there is no link in the text, the
     * result will be an empty list.
     *
     * The string <code>test <a href="http://localhost">test</a></code> will result in
     * <code><a href="http://localhost">test</a></code>
     *
     * @param input string with a possible link
     * @return a list with the links.
     */
    List<String> extractLinks(String input) {
        return linksAsStream(input).map(Element::outerHtml).collect(toList());
    }

    private Stream<Element> linksAsStream(String input) {
        return asStream(input, A_HREF);
    }

    private Stream<Element> asStream(String input, String selector) {
        return parse(input).select(selector).stream();
    }
}
