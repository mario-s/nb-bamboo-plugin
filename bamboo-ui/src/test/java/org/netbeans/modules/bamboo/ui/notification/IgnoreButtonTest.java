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

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class IgnoreButtonTest {
    
    private IgnoreButton classUnderTest;
    
    private PlanVo plan;
    
    @Before
    public void setUp() {
        plan = new PlanVo("");
        classUnderTest = new IgnoreButton(plan);
    }

    /**
     * Test of actionPerformed method, of class IgnoreButton.
     */
    @Test
    public void testActionPerformed_PlanPresent_ExpectNoNotify() {
        classUnderTest.actionPerformed(null);

        assertThat(plan.isNotify(), is(false));
    }
    
}
