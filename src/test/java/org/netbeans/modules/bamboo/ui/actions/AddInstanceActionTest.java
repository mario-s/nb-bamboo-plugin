package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.ui.wizard.InstancePropertiesDisplayable;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class AddInstanceActionTest {
    
    private AddInstanceAction classUnderTest;
    
    @Mock
    private InstancePropertiesDisplayable dialogDisplayer;
    
    @Before
    public void setUp() {
        classUnderTest = new AddInstanceAction(){
            @Override
            InstancePropertiesDisplayable createDialog() {
                return dialogDisplayer;
            }
        };
    }
    
    /**
     * Test of actionPerformed method, of class AddInstanceAction.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
        verify(dialogDisplayer).show();
    }

   
    
}
