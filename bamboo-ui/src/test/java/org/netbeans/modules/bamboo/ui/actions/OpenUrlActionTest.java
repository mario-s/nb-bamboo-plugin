package org.netbeans.modules.bamboo.ui.actions;

import java.net.URL;
import javax.swing.Action;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.After;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.BDDMockito.given;

import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.LookupContext;
import org.netbeans.modules.bamboo.model.rcp.OpenableInBrowser;
import org.netbeans.modules.bamboo.ui.BrowserInstance;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.Lookup;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BrowserInstance.class})
public class OpenUrlActionTest {

    private OpenUrlAction classUnderTest;

    @Mock
    private OpenableInBrowser openableInBrowser;

    @Mock
    private BrowserInstance browserInstance;

    @Captor
    private ArgumentCaptor<URL> urlCaptor;

    @Before
    public void setUp() {
        Lookup lookup = LookupContext.Instance.getLookup();
        classUnderTest = new OpenUrlAction(lookup);
        setInternalState(classUnderTest, "browser", browserInstance);
        given(openableInBrowser.getUrl()).willReturn("http://netbeans.org");
    }

    @After
    public void shutDown() {
        LookupContext.Instance.remove(openableInBrowser);
    }
    
    @Test
    public void testGetName_ExpectBundle() {
        String name = (String) classUnderTest.getValue(Action.NAME);
        assertThat(name, equalTo(Bundle.CTL_OpenUrlAction()));
    }

    /**
     * Test of actionPerformed method, of class OpenUrlAction.
     */
    @Test
    public void testActionPerformed_NoInstance_UrlShouldNotBecalled() {
        classUnderTest.actionPerformed(null);
        verify(browserInstance, never()).showURL(urlCaptor.capture());
    }

    /**
     * Test of actionPerformed method, of class OpenUrlAction.
     */
    @Test
    public void testActionPerformed_Instance_UrlShouldBecalled() {
        LookupContext.Instance.add(openableInBrowser);
        classUnderTest.actionPerformed(null);
        verify(browserInstance).showURL(urlCaptor.capture());
    }

    @Test
    public void testIsEnabled_InstanceAvailable_ShouldBeTrue() {
        given(openableInBrowser.isAvailable()).willReturn(true);
        LookupContext.Instance.add(openableInBrowser);
        boolean result = classUnderTest.isEnabled();
        assertThat(result, is(true));
    }
    
    @Test
    public void testIsEnabled_InstanceNotAvailable_ShouldBeFalse() {
        given(openableInBrowser.isAvailable()).willReturn(false);
        LookupContext.Instance.add(openableInBrowser);
        boolean result = classUnderTest.isEnabled();
        assertThat(result, is(false));
    }
}
