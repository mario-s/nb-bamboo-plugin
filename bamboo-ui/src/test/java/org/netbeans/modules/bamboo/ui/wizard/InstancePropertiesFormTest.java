package org.netbeans.modules.bamboo.ui.wizard;

import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.event.DocumentEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.mock.MockInstanceManager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.openide.util.Lookup.getDefault;
import static org.mockito.Mockito.inOrder;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class InstancePropertiesFormTest {
    
    private static final String FOO = "foo";
    
    @Mock
    private InstanceManageable delegate;

    private Observable observable;

    @Mock
    private Observer observer;

    @Mock
    private DocumentEvent docEvent;

    @Mock
    private AbstractAction applyAction;

    private InstancePropertiesForm classUnderTest;

    @Before
    public void setUp() {
        MockInstanceManager manager
                = (MockInstanceManager) getDefault().lookup(InstanceManageable.class);
        manager.setDelegate(delegate);

        observable = new Observable();

        classUnderTest = new InstancePropertiesForm() {
            @Override
            void inform(String message) {
                observer.update(observable, message);
            }

            @Override
            void error(String message) {
                observer.update(observable, message);
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
    public void testInsertUpdate_NoName_ExpectActionDisabled() {
        classUnderTest.insertUpdate(docEvent);
        verify(applyAction).setEnabled(false);
        verify(observer).update(eq(observable), anyString());
    }

    /**
     * Test of removeUpdate method, of class InstancePropertiesForm.
     */
    @Test
    public void testRemoveUpdate_NoName_ExpectActionDisabled() {
        classUnderTest.removeUpdate(docEvent);
        verify(applyAction).setEnabled(false);
    }

    /**
     * Test of insertUpdate method, of class InstancePropertiesForm.
     */
    @Test
    public void testInsertUpdate_ExistingName_ExpectActionDisabled() {
        classUnderTest.getTxtName().setText(FOO);
        
        InOrder order = inOrder(applyAction, delegate, observer);
        order.verify(applyAction).setEnabled(false);
        order.verify(delegate).existsInstance(FOO);
        order.verify(observer).update(eq(observable), anyString());
    }
}
