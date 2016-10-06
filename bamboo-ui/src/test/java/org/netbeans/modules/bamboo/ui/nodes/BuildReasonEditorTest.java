package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import javax.swing.JLabel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildReasonEditorTest {
    
    @Mock
    private PropertyEnv env;
    
    private BuildReasonEditor classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new BuildReasonEditor();
    }
    
    /**
     * Test of getCustomEditor method, of class BuildReasonEditor.
     */
    @Test
    public void testGetCustomEditor_ExpectInstanceOfJLabel() {
        Component result = classUnderTest.getCustomEditor();
        assertThat(result instanceof JLabel, is(true));
    }

    /**
     * Test of getInplaceEditor method, of class BuildReasonEditor.
     */
    @Test
    public void testGetInplaceEditor_ExpectNotNull() {
        InplaceEditor result = classUnderTest.getInplaceEditor();
        assertThat(result, notNullValue());
    }
    
    @Test
    public void testAttachEnv() {
        classUnderTest.attachEnv(env);
        verify(env).registerInplaceEditorFactory(classUnderTest);
    }

}
