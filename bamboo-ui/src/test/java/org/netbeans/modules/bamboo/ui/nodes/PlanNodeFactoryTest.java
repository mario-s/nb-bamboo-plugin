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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.openide.nodes.Node;

/**
 *
 * @author Mario Schroeder
 */
public class PlanNodeFactoryTest {

    private static final String FOO = "foo";
    private static final String BAR = "bar";
    private static final String BAZ = "baz";
    
    private ProjectVo project;

    private PlanNodeFactory classUnderTest;

    @Before
    public void setUp() {
        project = new ProjectVo("");
        classUnderTest = new PlanNodeFactory(project);
    }

    /**
     * Test of refreshNodes method, of class PlanNodeFactory.
     */
    @Test
    public void testRefreshNodes() {
        classUnderTest.refreshNodes();
    }

    /**
     * Test of createNodeForKey method, of class PlanNodeFactory.
     */
    @Test
    public void testCreateNodeForKey() {
        PlanVo key = new PlanVo(FOO, FOO);
        Node result = classUnderTest.createNodeForKey(key);
        assertThat(result.getName(), equalTo(FOO));
    }

    /**
     * Test of createKeys method, of class PlanNodeFactory.
     */
    @Test
    public void testCreateKeys_NoneIgnore_ExpectSorted() {
        List<PlanVo> toPopulate = new ArrayList<>();
        toPopulate.add(new PlanVo(FOO, FOO));
        toPopulate.add(new PlanVo(BAR, BAR));
        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.get(0).getName(), equalTo(BAR));
    }
    
     /**
     * Test of createKeys method, of class PlanNodeFactory.
     */
    @Test
    public void testCreateKeys_OneIgnore_ExpectSorted() {
        List<PlanVo> toPopulate = new ArrayList<>();
        PlanVo fooPlan = new PlanVo(FOO, FOO);
        fooPlan.setNotify(true);
        toPopulate.add(fooPlan);
        toPopulate.add(new PlanVo(BAZ, BAZ));
        toPopulate.add(new PlanVo(BAR, BAR));
        classUnderTest.createKeys(toPopulate);
        assertThat(toPopulate.get(2).getName(), equalTo(FOO));
    }

}
