package org.netbeans.modules.bamboo.rest;

import java.util.Map;
import java.util.Optional;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.rest.Info;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiCallerTest {

    private static final String FOO = "foo";

    @Mock
    private InstanceValues values;
    @Mock
    private WebTargetFactory webTargetFactory;
    @Mock
    private WebTarget target;
    @Mock
    private Invocation.Builder builder;
    @Mock
    private Response response;

    private ApiCaller<Info> classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new ApiCaller<>(new CallParameters(Info.class, values));
        setInternalState(classUnderTest, "webTargetFactory", webTargetFactory);

        given(values.getPassword()).willReturn(FOO.toCharArray());
        given(values.getUrl()).willReturn(FOO);
        given(webTargetFactory.newTarget(anyString(), any(Map.class))).willReturn(target);
        given(target.path(anyString())).willReturn(target);
        given(target.queryParam(anyString(), any())).willReturn(target);
    }

    /**
     * Test of createTarget method, of class ApiCaller.
     */
    @Test
    public void testCreateTarget_EmptyValues_ExpectNotPresent() {
        Optional<WebTarget> result = classUnderTest.createTarget();
        assertThat(result.isPresent(), is(false));
    }

    /**
     * Test of createTarget method, of class ApiCaller.
     */
    @Test
    public void testCreateTarget_Values_ExpectPresent() {
        given(values.getUsername()).willReturn(FOO);
        Optional<WebTarget> result = classUnderTest.createTarget();
        assertThat(result.isPresent(), is(true));
    }

    /**
     * Test of newTarget method, of class ApiCaller.
     */
    @Test
    public void testNewTarget_ExpectNotNull() {
        WebTarget result = classUnderTest.newTarget();
        assertThat(result, notNullValue());
    }

    /**
     * Test of doGet method, of class ApiCaller.
     */
    @Test
    public void testGetRequest_ExpectNotNull() {
        given(target.request()).willReturn(builder);
        given(builder.accept(anyString())).willReturn(builder);
        given(builder.get(Info.class)).willReturn(new Info());

        Info result = classUnderTest.doGet(target);
        assertThat(result, notNullValue());
    }

    /**
     * Test of doPost method, of class ApiCaller.
     */
    @Test
    public void testPostRequest_ExpectZero() {
        given(target.request()).willReturn(builder);
        given(builder.post(any(Entity.class))).willReturn(response);

        Response result = classUnderTest.doPost(target);
        assertThat(result.getStatus(), is(0));
    }

}
