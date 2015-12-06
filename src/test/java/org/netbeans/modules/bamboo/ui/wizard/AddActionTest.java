package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.Dialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.BambooInstanceConstants;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class AddActionTest {
    
    private static final String NAME = AddActionTest.class.getName();
    
    @Mock
    private Dialog dialog;
    
    @Mock
    private InstancePropertiesForm form;
    
    private AddAction classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new AddAction(dialog, form) {
            @Override
            void dispose() {
                dialog.dispose();
            }
        };
        
        given(form.getInstanceName()).willReturn(NAME);
    }
    
    @After
    public void shutDown() {
        BambooInstanceConstants.instancesPrefs().remove(NAME);
    }
    
    /**
     * Test of actionPerformed method, of class AddAction.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
        verify(dialog).dispose();
    }
}
