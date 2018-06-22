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

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rcp.FileVo;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

import static org.mockito.Mockito.verify;

import org.openide.util.Lookup;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter.Changes;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class ShowChangesActionTest {
    
    private static final String FOO = "foo";
    
    @Mock
    private BambooInstance instance;
   
    private PlanVo plan;

    private ShowChangesAction classUnderTest;
    
    private boolean available;

    @Before
    public void setUp() {
        plan = new PlanVo(FOO){
            @Override
            public boolean isAvailable() {
                return available;
            }
            
        };
        
        Lookup lookup = LookupContext.Instance.getLookup();
        classUnderTest = new ShowChangesAction(lookup);
    }
    
    @After
    public void shutDown() {
        LookupContext.Instance.remove(plan);
    }
    
    @Test
    public void testCreateContextAwareAction_ExpectNotNull() {
        assertThat(new ShowChangesAction().createContextAwareInstance(Lookup.EMPTY), notNullValue());
    }
    
    @Test
    public void testGetName_ExpectBundle() {
        String name = (String) classUnderTest.getValue(Action.NAME);
        assertThat(name, equalTo(Bundle.CTL_ShowChangesAction()));
    }
    
    @Test
    public void testIsEnabled_NoPlan_ExpectFalse() {
        boolean result = classUnderTest.isEnabled();
        assertThat(result, is(false));
    }

    
     /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    public void testActionPerformed() {
        
        LookupContext.Instance.add(plan);
        
        classUnderTest.actionPerformed(null);
    }
    
     /**
     * Test of actionPerformed method, of class QueuePlanAction.
     */
    @Test
    public void testResultChanged_PlanEnabled_ExpectEnabled() {
        
        available = true;
        LookupContext.Instance.add(plan);
        
        boolean result = classUnderTest.isEnabled();
        assertThat(result, is(true));
    }

    @Test
    public void testRun_ChangesNotPresent_ExpectInvoke() {
        ProjectVo project = new ProjectVo(FOO);
        project.setParent(instance);
        plan.setParent(project);
        ResultVo result = new ResultVo();
        plan.setResult(result);
        
        classUnderTest.doRun(result);
        
        verify(instance).expand(result, Changes);
    }
    
    
    @Test
    public void testRun_ChangesPresent_NotExpectInvoke() {
        ProjectVo project = new ProjectVo(FOO);
        project.setParent(instance);
        plan.setParent(project);
        
        FileVo file = new FileVo();
        file.setName(FOO);
        ChangeVo change = new ChangeVo();
        change.setComment(FOO);
        change.setFiles(singletonList(file));
        ResultVo result = new ResultVo();
        result.setBuildReason(FOO);
        result.setChanges(singletonList(change));
        
        plan.setResult(result);
        
        classUnderTest.doRun(result);
        
        verify(instance, never()).expand(result, Changes);
    }
}
