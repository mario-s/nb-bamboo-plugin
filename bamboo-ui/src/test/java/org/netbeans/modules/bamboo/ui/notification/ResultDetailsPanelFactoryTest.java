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
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author Mario Schroeder
 */
class ResultDetailsPanelFactoryTest {
    private static final String FOO = "foo";
    
    private ResultDetailsPanelFactory classUnderTest;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new ResultDetailsPanelFactory();
    }

    /**
     * Test of create method, of class ResultDetailsPanelFactory.
     */
    @Test
    void testCreate_StringString_ExpectPanel() {
        ResultDetailsPanel result = classUnderTest.create(FOO, FOO);
        assertNotNull(result);
    }

    /**
     * Test of create method, of class ResultDetailsPanelFactory.
     */
    @Test
    void testCreate_StringBuildResult__ExpectPanel() {
        BuildResult buildResult = new BuildResult(new PlanVo(FOO), new ResultVo(), new ResultVo());
        ResultDetailsPanel result = classUnderTest.create(FOO, buildResult);
        assertNotNull(result);
    }
    
}
