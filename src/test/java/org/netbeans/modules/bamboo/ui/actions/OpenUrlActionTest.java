package org.netbeans.modules.bamboo.ui.actions;

import java.net.URL;
import static java.util.Collections.singletonList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.BDDMockito.given;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.OpenableInBrowser;
import org.openide.awt.HtmlBrowser.URLDisplayer;

@RunWith(MockitoJUnitRunner.class)
public class OpenUrlActionTest {
    
    private OpenUrlAction classUnderTest;
    
    @Mock
    private OpenableInBrowser openableInBrowser;
    
    @Mock
    private URLDisplayer urlDisplayer;
    
    @Captor
    private ArgumentCaptor<URL> urlCaptor;
    
    @Before
    public void setUp() {
        classUnderTest = new OpenUrlAction(singletonList(openableInBrowser));
        classUnderTest.setUrlDisplayer(urlDisplayer);
        given(openableInBrowser.getUrl()).willReturn("http://netbeans.org");
    }
    
    /**
     * Test of actionPerformed method, of class OpenUrlAction.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
        verify(urlDisplayer).showURL(urlCaptor.capture());
    }
}
