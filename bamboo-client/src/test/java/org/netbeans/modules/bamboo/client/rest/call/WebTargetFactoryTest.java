package org.netbeans.modules.bamboo.client.rest.call;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class WebTargetFactoryTest {
    private static final String FOO = "foo";
    @Mock
    private Client client;
    @Mock
    private InstanceValues values;
    @Mock
    private WebTarget target;
    @InjectMocks
    private WebTargetFactory classUnderTest;
    
    @Before
    public void setUp() {
        setInternalState(classUnderTest, "client", client);
        
        given(values.getUrl()).willReturn(FOO);
        given(values.getPassword()).willReturn(new char[]{'a'});
        
        given(client.target(FOO)).willReturn(target);
        given(target.path(WebTargetFactory.REST_API)).willReturn(target);
        given(target.path(FOO)).willReturn(target);
        given(target.queryParam(anyString(), anyString())).willReturn(target);
    }

    /**
     * Test of newTarget method, of class WebTargetFactory.
     */
    @Test
    public void testNewTarget_NoParams_ExpectTarget() {
        WebTarget result = classUnderTest.newTarget(FOO, null);
        assertThat(result, notNullValue());
    }
    
      /**
     * Test of newTarget method, of class WebTargetFactory.
     */
    @Test
    public void testNewTarget_WithParams_ExpectTarget() {
        Map<String, String> params = new HashMap<>();
        params.put(FOO, FOO);
        WebTarget result = classUnderTest.newTarget(FOO, params);
        assertThat(result, notNullValue());
    }
    
}
