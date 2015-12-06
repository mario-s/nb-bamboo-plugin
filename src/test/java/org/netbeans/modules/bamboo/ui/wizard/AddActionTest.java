package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.BambooInstanceConstants;
import org.netbeans.modules.bamboo.BambooManager;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class AddActionTest {
    
    private static final String NAME = AddActionTest.class.getName();
    
    @Mock
    private Dialog dialog;
    
    @Mock
    private InstancePropertiesForm form;
    
    @Mock
    private LookupListener listener;

    private AddAction classUnderTest;
    
    private Lookup.Result<BambooInstance> result;

    @Before
    public void setUp() {
        classUnderTest = new AddAction(dialog, form) {
            @Override
            void dispose() {
                dialog.dispose();
            }
        };
        
        result = BambooManager.Instance.getLookup().lookupResult(
                BambooInstance.class);
        result.addLookupListener(listener);
        
        
        given(form.getInstanceName()).willReturn(NAME);
    }
    
    @After
    public void shutDown() {
        BambooInstanceConstants.instancesPrefs().remove(NAME);
    }
    
    /**
     * Test of actionPerformed method, of class AddAction.
     */
    @Test
    public void testActionPerformed() {
        classUnderTest.actionPerformed(null);
        verify(dialog).dispose();
    }
}
