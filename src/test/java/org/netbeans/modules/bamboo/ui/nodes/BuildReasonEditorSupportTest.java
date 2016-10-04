package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyEditor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.bamboo.model.ResultVo;

/**
 *
 * @author spindizzy
 */
public class BuildReasonEditorSupportTest {
    
    private static final String FOO = "foo";
    
    private BuildReasonEditorSupport classUnderTest;
    
    private ResultVo resultVo;
    
    @Before
    public void setUp() {
        resultVo = new ResultVo();
        classUnderTest = new BuildReasonEditorSupport(resultVo);
    }

    /**
     * Test of getValue method, of class BuildReasonEditorSupport.
     */
    @Test
    public void testGetValue_NoReason_ExpectEmptyString() throws Exception {
        String result = classUnderTest.getValue();
        assertThat(result, equalTo(""));
    }

    /**
     * Test of getValue method, of class BuildReasonEditorSupport.
     */
    @Test
    public void testGetValue_Reason_ExpectString() throws Exception {
        resultVo.setBuildReason(FOO);
        String result = classUnderTest.getValue();
        assertThat(result, equalTo(FOO));
    }

    /**
     * Test of getPropertyEditor method, of class BuildReasonEditorSupport.
     */
    @Test
    public void testGetPropertyEditor_NoLink_ExpectEditor() {
        resultVo.setBuildReason(FOO);
        PropertyEditor result = classUnderTest.getPropertyEditor();
        assertThat(result, notNullValue(PropertyEditor.class));
    }
    
    
     /**
     * Test of getPropertyEditor method, of class BuildReasonEditorSupport.
     */
    @Test
    public void testGetPropertyEditor_Link_ExpectEditor() {
        resultVo.setBuildReason("<a href=\"http://localhost\">test</a>");
        PropertyEditor result = classUnderTest.getPropertyEditor();
        assertThat(result, notNullValue(PropertyEditor.class));
    }
}
