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
package org.netbeans.modules.bamboo.model.convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;

/**
 * Convert a a string to a {@link LocalDateTime}. The result will be empty if convert failed.
 */
@Log
class LocalDateTimeConverter implements VoConverter<String, Optional<LocalDateTime>> {
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Override
    public Optional<LocalDateTime> convert(String src) {
        Optional<LocalDateTime> opt = Optional.empty();
        if (StringUtils.isNotBlank(src)) {
            try {
                DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder().appendPattern(DATE_PATTERN);
                getOffset(src).ifPresent((offset) -> builder.appendOffset("+HH:MM", offset));
                opt = Optional.of(LocalDateTime.parse(src, builder.toFormatter()));
            } catch (DateTimeParseException ex) {
                log.fine(ex.getMessage());
            }
        }
        return opt;
    }

    private Optional<String> getOffset(String src) {
        int pos = src.lastIndexOf("+");
        return (pos > 0) ? Optional.of(src.substring(pos, src.length())) : Optional.empty();
    }
    
}
