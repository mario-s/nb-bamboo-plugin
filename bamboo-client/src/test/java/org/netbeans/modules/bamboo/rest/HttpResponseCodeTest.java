package org.netbeans.modules.bamboo.rest;

import java.util.Optional;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class HttpResponseCodeTest {

    /**
     * Test of getCode method, of class HttpResponseCode.
     */
    @Test
    public void testGetCode_Unauthorized() {
        Optional<HttpResponseCode> result = HttpResponseCode.getCode(401);
        assertThat(result.isPresent(), is(true));
    }
    
}
