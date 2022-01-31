/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.ui.nodes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.netbeans.modules.bamboo.model.LifeCycleState;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class PlanNodeTest {

    private static final String FOO = "foo";

    @Mock
    private PropertyChangeListener listener;

    private PlanVo plan;

    private PlanNode classUnderTest;

    @BeforeEach
    void setUp() {
        plan = new PlanVo(FOO);
        plan.setShortName(FOO);
        ResultVo resultVo = new ResultVo(FOO);
        resultVo.setNumber(1);
        plan.setResult(resultVo);
        plan.setNotify(false);
        
        ProjectVo project = new ProjectVo(FOO);
        plan.setParent(project);
        
        classUnderTest = new PlanNode(plan);
    }

    /**
     * Test of getHtmlDisplayName method, of class PlanNode.
     */
    @Test
    void testGetHtmlDisplayName_ExpectNotFinishedLifyCycle() {
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertFalse(htmlDisplayName.contains(LifeCycleState.Finished.name()));
    }

    /**
     * Test of propertyChange method, of class PlanNode.
     */
    @Test
    void testChange_Name() {
        ResultVo result = new ResultVo();
        result.setNumber(2);
        result.setState(State.Failed);
        plan.setResult(result);
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertTrue(htmlDisplayName.contains(State.Failed.toString()));
    }
    
     /**
     * Test of propertyChange method, of class PlanNode.
     */
    @Test
    void testChange_Watching() {
        String htmlDisplayName = classUnderTest.getHtmlDisplayName();
        assertTrue(htmlDisplayName.contains(Bundle.TXT_Plan_Not_Watched()));
    }

    /**
     * Test of propertyChange method, of class PlanNode.
     */
    @Test
    void testChange_BuildNumber() throws IllegalAccessException, InvocationTargetException {

        Property[] oldProps = getProperties(0);
        Object oldValue = oldProps[1].getValue();

        ResultVo result = new ResultVo();
        result.setNumber(2);
        result.setState(State.Failed);
        plan.setResult(result);

        Property[] newProps = getProperties(0);

        Object newValue = newProps[2].getValue();

        assertFalse(oldValue.equals(newValue));
    }

    private Property[] getProperties(int setIndex) {
        Node.PropertySet[] oldSets = classUnderTest.getPropertySets();
        Node.PropertySet oldPropSet = oldSets[setIndex];
        return oldPropSet.getProperties();
    }

    @Test
    void testSetSilent_ExpectFirePropertyChange() {
        plan.getParent().ifPresent(p -> {p.addPropertyChangeListener(listener);});
        plan.setNotify(true);
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }
}
