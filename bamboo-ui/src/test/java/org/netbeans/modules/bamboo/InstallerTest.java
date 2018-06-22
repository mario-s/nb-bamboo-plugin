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
package org.netbeans.modules.bamboo;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.inOrder;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.model.rcp.BambooInstance;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import org.netbeans.modules.bamboo.mock.MockInstanceManager;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.Task;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.anyBoolean;


/**
 */
@RunWith(MockitoJUnitRunner.class)
public class InstallerTest {
    @Mock
    private InstanceManageable delegate;

    @Mock
    private BambooInstance instance;

    private Task task;

    private Installer classUnderTest;

    @Before
    public void setUp() {
        MockInstanceManager manager =
            (MockInstanceManager) getDefault().lookup(InstanceManageable.class);
        manager.setDelegate(delegate);

        task = new Task(null);

        given(delegate.loadInstances()).willReturn(singletonList(instance));
        given(instance.synchronize(anyBoolean())).willReturn(task);

        classUnderTest = new Installer();
    }

    /**
     * Test of run method, of class Installer.
     */
    @Test
    public void testRun() {
        classUnderTest.run();

        InOrder order = inOrder(instance, delegate);
        order.verify(instance).synchronize(false);
        order.verify(delegate).addInstance(instance);
    }
}
