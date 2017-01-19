package org.netbeans.modules.bamboo.model.rcp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class AvailabilityVerifierTest {

    @Mock
    private BambooInstance instance;

    private ProjectVo project;

    @Before
    public void setUp() {
        project = new ProjectVo("");
    }

    /**
     * Test of isAvailable method, of class AvailabilityVerifier.
     */
    @Test
    public void testIsAvailable_NoParent_ExpectFalse() {
        boolean result = AvailabilityVerifier.isAvailable(project);
        assertThat(result, is(false));
    }

    @Test
    public void testIsAvailable_ParentPresent_ExpectTrue() {
        given(instance.isAvailable()).willReturn(true);
        project.setParent(instance);
        boolean result = AvailabilityVerifier.isAvailable(project);
        assertThat(result, is(true));
    }

}
