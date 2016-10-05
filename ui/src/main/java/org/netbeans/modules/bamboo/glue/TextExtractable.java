package org.netbeans.modules.bamboo.glue;

/**
 * This interface defines methods to handle strings with HTML markup.
 * @author spindizzy
 */
public interface TextExtractable {

    /**
     * This method returns true if the text contains a link otherwhise false.
     *
     * @param text the text which may contain a HTML link
     * @return true if the text contains a link otherwhise false.
     */
    default boolean containsLink(String text){
        return !extractLink(text).isEmpty();
    }

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
    String extractLink(String text);

    /**
     * Returns the normal text, which is embedded within HTML tags.
     *
     * The string <code><a href="http://localhost">test</a></code> will result in <code>test</code>
     *
     * @param text string with possible HTML content.
     * @return normal text or empty string.
     */
    String extractNormalText(String text);

    /**
     * This methods returns the url from the given text. If there is no url in the text, the result will be an empty
     * string.
     *
     * The string <code><a href="http://localhost">test</a>s/code> will result in <code>http://localhost</code>
     *
     * @param text string with a possible url
     * @return url as string or empty string.
     */
    String extractUrl(String text);

    /**
     * This method returns a sub string from the givin complte string till the first appearance of the string to be removed.
     * @param complete the complete string
     * @param toRemove the part to be removed
     * @return the trimmed string
     */
    String substring(String complete, String toRemove);
}
