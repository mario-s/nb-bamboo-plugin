package org.netbeans.modules.bamboo.ui.ext;

import javax.swing.JComponent;
import javax.swing.JLabel;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
 */
public class ComponentFactoryTest {
    
    private ComponentFactory classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new ComponentFactory();
    }

    /**
     * Test of create method, of class ComponentFactory.
     */
    @Test
    public void testCreate_NormalString_ExpectJLabel() {
        String text = "foo";
        JComponent result = classUnderTest.create(text);
        assertThat(result instanceof JLabel, is(true));
    }
    
     /**
     * Test of create method, of class ComponentFactory.
     */
    @Test
    public void testCreate_NormalString_ExpectLinkLabel() {
        String text = "test <a href=\"http://localhost\">test</a>";
        JComponent result = classUnderTest.create(text);
        assertThat(result.getComponentCount(), is(2));
    }
    
}
