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

import java.net.URL;
import javax.swing.Action;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.BDDMockito.given;

import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.OpenableInBrowser;
import org.netbeans.modules.bamboo.ui.BrowserInstance;
import org.openide.util.Lookup;

import static org.mockito.Mockito.never;

import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class OpenUrlActionTest {

    private OpenUrlAction classUnderTest;

    @Mock
    private OpenableInBrowser openableInBrowser;

    @Mock
    private BrowserInstance browserInstance;

    @Captor
    private ArgumentCaptor<URL> urlCaptor;

    @BeforeEach
    void setUp() {
        Lookup lookup = LookupContext.Instance.getLookup();
        classUnderTest = new OpenUrlAction(lookup);
        ReflectionTestUtils.setField(classUnderTest, "browser", browserInstance);
    }

    @AfterEach
    void shutDown() {
        LookupContext.Instance.remove(openableInBrowser);
    }

    @Test
    void testCreateContextAwareAction_ExpectNotNull() {
        assertNotNull(new OpenUrlAction().createContextAwareInstance(Lookup.EMPTY));
    }

    @Test
    void testGetName_ExpectBundle() {
        String name = (String) classUnderTest.getValue(Action.NAME);
        assertEquals(Bundle.CTL_OpenUrlAction(), name);
    }

    /**
     * Test of actionPerformed method, of class OpenUrlAction.
     */
    @Test
    void testActionPerformed_NoInstance_UrlShouldNotBecalled() {
        classUnderTest.actionPerformed(null);
        verify(browserInstance, never()).showURL(urlCaptor.capture());
    }

    /**
     * Test of actionPerformed method, of class OpenUrlAction.
     */
    @Test
    void testActionPerformed_Instance_UrlShouldBecalled() {
        given(openableInBrowser.getUrl()).willReturn("http://netbeans.org");
        LookupContext.Instance.add(openableInBrowser);
        classUnderTest.actionPerformed(null);
        verify(browserInstance).showURL(urlCaptor.capture());
    }

    @Test
    void testIsEnabled_InstanceAvailable_ShouldBeTrue() {
        given(openableInBrowser.isAvailable()).willReturn(true);
        LookupContext.Instance.add(openableInBrowser);
        assertTrue(classUnderTest.isEnabled());
    }

    @Test
    void testIsEnabled_InstanceNotAvailable_ShouldBeFalse() {
        given(openableInBrowser.isAvailable()).willReturn(false);
        LookupContext.Instance.add(openableInBrowser);
        assertFalse(classUnderTest.isEnabled());
    }
}
