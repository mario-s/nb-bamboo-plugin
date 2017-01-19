package org.netbeans.modules.bamboo.client.glue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class HttpResponseCodeTest {

    /**
     * Test of getCode method, of class HttpResponseCode.
     */
    @Test
    public void testGetCode_Unauthorized() {
        HttpResponseCode result = HttpResponseCode.getCode(401);
        assertThat(result, is(HttpResponseCode.Unauthorized));
    }
    
     /**
     * Test of getCode method, of class HttpResponseCode.
     */
    @Test
    public void testGetCode_Unknown() {
        HttpResponseCode result = HttpResponseCode.getCode(-10);
        assertThat(result, is(HttpResponseCode.Unknown));
    }
    
}
