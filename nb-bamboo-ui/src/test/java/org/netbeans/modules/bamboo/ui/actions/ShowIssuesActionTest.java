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
package org.netbeans.modules.bamboo.ui.actions;

import javax.swing.Action;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

import static org.mockito.Mockito.verify;

import org.openide.util.Lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.JIRA;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class ShowIssuesActionTest {
    
    private static final String FOO = "foo";
    
    @Mock
    private BambooInstance instance;
   
    private PlanVo plan;

    private ShowIssuesAction classUnderTest;
    
    private boolean available;

    @BeforeEach
    void setUp() {
        plan = new PlanVo(FOO){
            @Override
            public boolean isAvailable() {
                return available;
            }
            
        };
        
        Lookup lookup = LookupContext.Instance.getLookup();
        classUnderTest = new ShowIssuesAction(lookup);
    }
    
    @AfterEach
    void shutDown() {
        LookupContext.Instance.remove(plan);
    }
    
    @Test
    void testCreateContextAwareAction_ExpectNotNull() {
        assertNotNull(new ShowChangesAction().createContextAwareInstance(Lookup.EMPTY));
    }
    
    @Test
    void testGetName_ExpectBundle() {
        String name = (String) classUnderTest.getValue(Action.NAME);
        assertEquals(Bundle.CTL_ShowIssuesAction(), name);
    }
    
    @Test
    void testIsEnabled_NoPlan_ExpectFalse() {
        assertFalse(classUnderTest.isEnabled());
    }

    
     /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    void testActionPerformed() {
        
        LookupContext.Instance.add(plan);
        classUnderTest.actionPerformed(null);
    }
    
     /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    void testResultChanged_PlanEnabled_ExpectEnabled() {
        available = true;
        LookupContext.Instance.add(plan);
        
        assertTrue(classUnderTest.isEnabled());
    }

    @Test
    void testRun_Issues_ExpectInvoke() {
        ProjectVo project = new ProjectVo(FOO);
        project.setParent(instance);
        plan.setParent(project);
        ResultVo result = new ResultVo();
        plan.setResult(result);
        
        classUnderTest.process(plan);
        
        verify(instance).expand(result, JIRA);
    }
    
}
