package org.netbeans.modules.bamboo.ui.actions;

import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.api.io.OutputWriter;

/**
 *
 * @author spindizzy
 */
public class InputOutputProviderTest {
    
    private InputOutputProvider classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new InputOutputProvider();
    }

    /**
     * Test of getOut method, of class InputOutputProvider.
     */
    @Test
    public void testGetOut() {
        OutputWriter result = classUnderTest.getOut("");
        assertThat(result, notNullValue());
    }
    
}
