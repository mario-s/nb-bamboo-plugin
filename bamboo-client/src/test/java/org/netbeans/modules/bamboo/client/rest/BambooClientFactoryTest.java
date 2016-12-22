package org.netbeans.modules.bamboo.client.rest;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.InstanceValues;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import org.netbeans.modules.bamboo.client.glue.BambooClient;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooClientFactoryTest {
    private static final String FOO = "foo";
    
    @Mock
    private HttpUtility httpUtility;
    
    @Mock
    private InstanceValues values;
    
    private DefaultBambooClientFactory classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new DefaultBambooClientFactory(){
            @Override
            HttpUtility newUtility() {
                return httpUtility;
            }
        };
        given(values.getUrl()).willReturn(FOO);
    }
    
    
    /**
     * Test of newClient method, of class DefaultBambooClientFactory.
     */
    @Test
    public void testNewClient_UrlExists_ExpectPresent() {
        given(httpUtility.exists(FOO)).willReturn(true);
        
        Optional<BambooClient> result = classUnderTest.newClient(values);
        assertThat(result.isPresent(), is(true));
    }
    
    /**
     * Test of newClient method, of class DefaultBambooClientFactory.
     */
    @Test
    public void testNewClient_UrlDoesNotExists_ExpectAbsent() {
        given(httpUtility.exists(FOO)).willReturn(false);
        
        Optional<BambooClient> result = classUnderTest.newClient(values);
        assertThat(result.isPresent(), is(false));
    }
    
}
