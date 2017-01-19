package org.netbeans.modules.bamboo.ui.util;

import java.time.LocalDateTime;
import org.junit.Test;

import static java.time.LocalDateTime.now;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class DateFormatterTest {
    
    /**
     * Test of format method, of class DateFormatter.
     */
    @Test
    public void testFormat_Null_ExpectEmptyString() {
        LocalDateTime src = null;
        String result = DateFormatter.format(src);
        assertThat(result.isEmpty(), is(true));
    }
    
     /**
     * Test of format method, of class DateFormatter.
     */
    @Test
    public void testFormat_Now_ExpectNonEmptyString() {
        LocalDateTime src = now();
        String result = DateFormatter.format(src);
        assertThat(result.isEmpty(), is(false));
    }
    
}
