package org.netbeans.modules.bamboo.ui.wizard;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.glue.InstanceValues;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class AddInstanceWorkerTest {
    @Mock
    private AbstractDialogAction action;
    @Mock
    private InstanceManageable instanceManager;
    @Mock
    private InstancePropertiesForm form;
    
    private AddInstanceWorker classUnderTest;
    
    @Before
    public void setUp() {
        given(action.getInstanceManager()).willReturn(instanceManager);
        given(action.getForm()).willReturn(form);
        classUnderTest = new AddInstanceWorker(action);
    }

    /**
     * Test of doInBackground method, of class AddInstanceWorker.
     */
    @Test
    public void testDoInBackground() throws Exception {
        Object result = classUnderTest.doInBackground();
        assertNull(result);
        verify(instanceManager).addInstance(any(InstanceValues.class));
    }

    /**
     * Test of done method, of class AddInstanceWorker.
     */
    @Test
    public void testDone() {
        classUnderTest.done();
        verify(action).onDone();
    }
    
}
