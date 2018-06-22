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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * This class formats dates to a string suitable for this module.
 * 
 * @author Mario Schroeder
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
