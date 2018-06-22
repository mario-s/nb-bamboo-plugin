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
package org.netbeans.modules.bamboo.client.rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.client.rest.AbstractVoUpdater.PlansUpdater;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Mario Schroeder
 */
@RunWith(MockitoJUnitRunner.class)
public class PlansUpdaterTest {

    private static final String FOO = "foo";
    private static final String BAR = "bar";

    @Mock
    private PropertyChangeListener listener;

    private PlansUpdater classUnderTest;

    private List<PlanVo> source;

    private List<PlanVo> target;

    @Before
    public void setUp() {
        classUnderTest = new PlansUpdater();
        source = new ArrayList<>();
        target = new ArrayList<>();
    }

    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_SameContent_NoChange() {
        PlanVo left = new PlanVo(FOO, FOO);
        PlanVo right = new PlanVo(FOO, FOO);

        source.add(left);
        target.add(right);

        classUnderTest.update(source, target);

        assertThat(target.get(0), equalTo(right));
    }

    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_DifferentContent_ExpectLeft() {
        PlanVo left = new PlanVo(FOO, FOO);
        PlanVo right = new PlanVo(BAR, BAR);

        source.add(left);
        target.add(right);

        classUnderTest.update(source, target);

        assertThat(target.get(0).getKey(), equalTo(FOO));
    }

    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_DifferentContent_OnlyOne() {
        PlanVo left = new PlanVo(FOO, FOO);
        PlanVo right = new PlanVo(BAR, BAR);

        source.add(left);
        target.add(right);
        target.add(left);

        classUnderTest.update(source, target);

        assertThat(target.size(), is(1));
    }

    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_AddContent_NewOne() {
        PlanVo left = new PlanVo(FOO, FOO);
        PlanVo right = new PlanVo(BAR, BAR);

        source.add(left);
        source.add(right);
        target.add(right);

        classUnderTest.update(source, target);

        assertThat(target.size(), is(2));
    }
    
        /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_Enable_ExpectListenerCalled() {
        PlanVo left = new PlanVo(FOO, FOO);
        left.setEnabled(true);
        PlanVo right = new PlanVo(FOO, FOO);
        right.addPropertyChangeListener(listener);

        source.add(left);
        target.add(right);

        classUnderTest.update(source, target);

        assertThat(left.isEnabled(), is(true));
        verify(listener).propertyChange(any(PropertyChangeEvent.class));
    }

    /**
     * Test of update method, of class ProjectsUpdater.
     */
    @Test
    public void testUpdate_NewResult() {
        PlanVo left = new PlanVo(FOO);
        ResultVo leftResult = new ResultVo();
        leftResult.setNumber(1);
        left.setResult(leftResult);

        PlanVo right = new PlanVo(FOO);
        ResultVo rightResult = new ResultVo();
        right.setResult(rightResult);

        source.add(left);
        target.add(right);

        classUnderTest.update(source, target);

        assertThat(target.get(0).getResult().getNumber(), is(1));
    }

}
