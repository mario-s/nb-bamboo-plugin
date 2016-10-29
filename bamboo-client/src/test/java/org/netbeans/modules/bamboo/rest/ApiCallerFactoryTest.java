package org.netbeans.modules.bamboo.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.InstanceValues;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiCallerFactoryTest {
    private static final String FOO = "foo";
    private static final Class BAR = FOO.getClass();
    @Mock
    private InstanceValues values;
    @InjectMocks
    private ApiCallerFactory classUnderTest;
    
    /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewCaller_ClassAndString_ExpectNotNull() {
        ApiCaller result = classUnderTest.newCaller(BAR, FOO);
        assertThat(result, notNullValue());
    }
    
    /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewCaller_WithParams_ExpectNotNull() {
        ApiCaller result = classUnderTest.newCaller(BAR, FOO, emptyMap());
        assertThat(result, notNullValue());
    }
    
     /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewCaller_JsonPath_ExpectNotNull() {
        ApiCaller result = classUnderTest.newCaller(BAR, FOO + ApiCallerFactory.JSON_PATH);
        assertThat(result, notNullValue());
    }


    /**
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewRepeatCaller_ClassAndString_ExpectNotNull() {
        ApiCallRepeater result = classUnderTest.newRepeatCaller(BAR, FOO);
        assertThat(result, notNullValue());
    }
    
    /**
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewRepeatCaller_WithParams_ExpectNotNull() {
        ApiCallRepeater result = classUnderTest.newRepeatCaller(BAR, FOO, emptyMap());
        assertThat(result, notNullValue());
    }
    
}
