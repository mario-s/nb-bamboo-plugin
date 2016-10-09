package org.netbeans.modules.bamboo.ui.wizard;

import javax.swing.AbstractAction;
import javax.swing.event.DocumentEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class InstancePropertiesFormTest {
    
    @Mock
    private DocumentEvent docEvent;
    
    @Mock
    private AbstractAction applyAction;
    
    private InstancePropertiesForm classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new InstancePropertiesForm() {
            @Override
            void inform(String message) {
            }

            @Override
            void error(String message) {
            }

            @Override
            void clearMessages() {
            }
        };
        
        classUnderTest.setApplyAction(applyAction);
    }

    /**
     * Test of block method, of class InstancePropertiesForm.
     */
    @Test
    public void testBlock_ExpectProgressVisisble() {
        classUnderTest.block();
        assertThat(classUnderTest.getProgressBar().isVisible(), is(true));
    }

    /**
     * Test of unblock method, of class InstancePropertiesForm.
     */
    @Test
    public void testUnblock_ExpectProgressNotVisisble() {
        classUnderTest.unblock();
        assertThat(classUnderTest.getProgressBar().isVisible(), is(false));
    }

    /**
     * Test of setFocus method, of class InstancePropertiesForm.
     */
    @Test
    public void testSetFocus() {
        classUnderTest.setFocus(0);
    }

    /**
     * Test of insertUpdate method, of class InstancePropertiesForm.
     */
    @Test
    public void testInsertUpdate() {
        classUnderTest.insertUpdate(docEvent);
        verify(applyAction).setEnabled(false);
    }

    /**
     * Test of removeUpdate method, of class InstancePropertiesForm.
     */
    @Test
    public void testRemoveUpdate() {
        classUnderTest.removeUpdate(docEvent);
        verify(applyAction).setEnabled(false);
    }

}
