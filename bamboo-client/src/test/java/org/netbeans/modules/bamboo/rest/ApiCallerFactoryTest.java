package org.netbeans.modules.bamboo.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.InstanceValues;

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
     * Test of newRepeatCaller method, of class ApiCallerFactory.
     */
    @Test
    public void testNewRepeatCaller_ClassAndString_ExpectNotNull() {
        RepeatApiCaller result = classUnderTest.newRepeatCaller(BAR, FOO);
        assertThat(result, notNullValue());
    }
    
}
