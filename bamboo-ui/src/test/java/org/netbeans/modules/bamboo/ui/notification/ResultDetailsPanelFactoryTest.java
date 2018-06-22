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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

/**
 *
 * @author Mario Schroeder
 */
public class ResultDetailsPanelFactoryTest {
    private static final String FOO = "foo";
    
    private ResultDetailsPanelFactory classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new ResultDetailsPanelFactory();
    }

    /**
     * Test of create method, of class ResultDetailsPanelFactory.
     */
    @Test
    public void testCreate_StringString_ExpectPanel() {
        ResultDetailsPanel result = classUnderTest.create(FOO, FOO);
        assertThat(result, not(nullValue()));
    }

    /**
     * Test of create method, of class ResultDetailsPanelFactory.
     */
    @Test
    public void testCreate_StringBuildResult__ExpectPanel() {
        BuildResult buildResult = new BuildResult(new PlanVo(FOO), new ResultVo(), new ResultVo());
        ResultDetailsPanel result = classUnderTest.create(FOO, buildResult);
        assertThat(result, not(nullValue()));
    }
    
}
