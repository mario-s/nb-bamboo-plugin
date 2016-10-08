package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import javax.swing.JPanel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openide.explorer.propertysheet.PropertyEnv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
    public void testGetCustomEditor_ExpectInstanceOfJEditor() {
        Component result = classUnderTest.getCustomEditor();
        assertThat(result instanceof JPanel, is(true));
    }


}
