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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;

import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 *
 * @author Mario Schroeder
 */
class IgnoreButtonTest {
    
    private IgnoreButton classUnderTest;
    
    private PlanVo plan;
    
    @BeforeEach
    void setUp() {
        plan = new PlanVo("");
        classUnderTest = new IgnoreButton(plan);
    }

    /**
     * Test of actionPerformed method, of class IgnoreButton.
     */
    @Test
    void testActionPerformed_PlanPresent_ExpectNoNotify() {
        classUnderTest.actionPerformed(null);

        assertFalse(plan.isNotify());
    }
    
}
