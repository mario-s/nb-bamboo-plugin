package org.netbeans.modules.bamboo.ui.wizard;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.rest.BambooInstanceProduceable;
import org.netbeans.modules.bamboo.rest.DefaultBambooInstance;

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
    private BambooInstanceProduceable bambooInstanceProducer;

    private DefaultBambooInstance instance;

    private AddInstanceWorker classUnderTest;

    private final String name;

    public AddInstanceWorkerTest() {
        name = getClass().getName();
    }

    @Before
    public void setUp() {


        given(action.getInstanceManager()).willReturn(instanceManager);

        instance = new DefaultBambooInstance();


        classUnderTest = new AddInstanceWorker(action);
        setInternalState(classUnderTest, "bambooInstanceProducer", bambooInstanceProducer);
    }

    /**
     * Test of doInBackground method, of class AddInstanceWorker.
     */
    @Test
    public void testCreateInstance() {
        setInternalState(classUnderTest, "values", new DefaultInstanceValues());
        given(bambooInstanceProducer.create(any(DefaultInstanceValues.class))).willReturn(instance);

        BambooInstance result = classUnderTest.createInstance();
        assertNotNull(result);
    }

}
