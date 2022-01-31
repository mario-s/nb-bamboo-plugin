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
package org.netbeans.modules.bamboo.ui.notification;


import static java.util.Collections.singletonList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.event.QueueEvent;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.openide.util.Lookup;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

/**
 *
 * @author Mario Schroeder
 */
@ExtendWith(MockitoExtension.class)
class PlanResultNotifyTest {

    @Mock
    private BambooInstance instance;

    @Spy
    private NotifyDelegator delegator;

    private PlanResultNotify classUnderTest;

    private PlanVo plan;

    private QueueEvent event;

    @BeforeEach
    void setUp() {
        plan = new PlanVo("a");
        plan.setName("test");
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(1);
        plan.setResult(resultVo);

        event = new QueueEvent();

        ProjectVo project = new ProjectVo("");
        project.setChildren(singletonList(plan));

        given(instance.getChildren()).willReturn(singletonList(project));

        Lookup lookup = LookupContext.Instance.getLookup();
        given(instance.getLookup()).willReturn(lookup);

        classUnderTest = new PlanResultNotify(instance);
        classUnderTest.setDelegator(delegator);
    }

    @AfterEach
    void shutDown() {
        LookupContext.Instance.remove(event);
    }

    private ResultVo newFailedResult() {
        ResultVo resultVo = new ResultVo();
        resultVo.setNumber(2);
        resultVo.setState(State.Failed);
        return resultVo;
    }

    /**
     * Test of propertyChange method, of class PlanResultNotify.
     */
    @Test
    void testPropertyChange_ExpectNotify() {
        ResultVo resultVo = newFailedResult();
        plan.setResult(resultVo);
        verify(delegator).notify(any(BuildResult.class));
    }
    
     /**
     * Test of propertyChange method, of class PlanResultNotify.
     */
    @Test
    void testPropertyChange_PlanIgnore_ExpectNoNotify() {
        plan.setNotify(false);
        ResultVo resultVo = newFailedResult();
        plan.setResult(resultVo);
        verify(delegator, never()).notify(any(BuildResult.class));
    }

    @Test
    void testResultChanged_ExpectNotify() {
        LookupContext.Instance.add(event);
        verify(delegator).notify(eq(event));
    }

}
