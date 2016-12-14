package org.netbeans.modules.bamboo.rest.call;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.InstanceValues;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.netbeans.modules.bamboo.glue.RestResources.JSON_PATH;

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
        ApiCallable result = classUnderTest.newCaller(BAR, FOO);
        assertThat(result, notNullValue());
    }
    
    /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewCaller_WithParams_ExpectNotNull() {
        ApiCallable result = classUnderTest.newCaller(BAR, FOO, emptyMap());
        assertThat(result, notNullValue());
    }
    
     /**
     * Test of newCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewCaller_JsonPath_ExpectNotNull() {
        ApiCallable result = classUnderTest.newCaller(BAR, FOO + JSON_PATH);
        assertThat(result, notNullValue());
    }


    /**
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewRepeatCaller_ClassAndString_ExpectNotNull() {
        ApiCallRepeatable result = classUnderTest.newRepeatCaller(BAR, FOO);
        assertThat(result, notNullValue());
    }
    
    /**
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewRepeatCaller_WithParams_ExpectNotNull() {
        ApiCallRepeatable result = classUnderTest.newRepeatCaller(BAR, FOO, emptyMap());
        assertThat(result, notNullValue());
    }
    
}
