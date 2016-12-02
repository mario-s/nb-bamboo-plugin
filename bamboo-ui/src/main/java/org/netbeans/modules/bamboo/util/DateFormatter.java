package org.netbeans.modules.bamboo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * This class formats dates to a string suitable for this module.
 * 
 * @author spindizzy
 */
public final class DateFormatter {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    
    private DateFormatter() {
    }
    
    /**
     * Format the source to a string. If the source is null, the result will be an empty string.
     * @param src {@link LocalDateTime}
     * @return either a formatted or empty string
     */
    public static String format(LocalDateTime src) {
        return (src != null) ? FORMATTER.format(src) : EMPTY;
    }
}
