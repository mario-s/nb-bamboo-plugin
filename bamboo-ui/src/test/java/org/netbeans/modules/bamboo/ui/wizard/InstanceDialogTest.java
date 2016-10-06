package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.Dialog;
import java.beans.PropertyChangeEvent;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class InstanceDialogTest {
    @Mock
    private Dialog dialog;
    
    @Mock
    private InstancePropertiesForm form;
    
    private InstanceDialog classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new InstanceDialog(form){
            @Override
            Dialog createDialog() {
                return dialog;
            }
        };
    }

    /**
     * Test of propertyChange method, of class InstanceDialog.
     */
    @Test
    public void testPropertyChange() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, WorkerEvents.INSTANCE_CREATED.name(), null, null);
        classUnderTest.propertyChange(event);
    }

    /**
     * Test of show method, of class InstanceDialog.
     */
    @Test
    public void testShow() {
        classUnderTest.show();
        verify(dialog).setVisible(true);
    }
    
}
