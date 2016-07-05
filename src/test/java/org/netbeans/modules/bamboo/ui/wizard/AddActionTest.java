package org.netbeans.modules.bamboo.ui.wizard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;

import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import org.mockito.runners.MockitoJUnitRunner;

import org.netbeans.modules.bamboo.glue.BambooManager;
import org.netbeans.modules.bamboo.glue.InstanceManageable;

import java.awt.Dialog;


/**
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class AddActionTest {
    private static final String NAME = AddActionTest.class.getName();

    @Mock
    private InstanceManageable manager;

    @Mock
    private Dialog dialog;

    @Mock
    private InstancePropertiesForm form;

    private AddAction classUnderTest;

    @Before
    public void setUp() {
        classUnderTest =
            new AddAction(dialog, form) {
                @Override
                void dispose() {
                    dialog.dispose();
                }
            };

        setInternalState(classUnderTest, "manager", manager);
        given(form.getInstanceName()).willReturn(NAME);
    }

    /**
     * Test of actionPerformed method, of class AddAction.
     */
    @Test
    public void testActionPerformed() {
        given(form.getPassword()).willReturn(new char[] { 'a' });
        classUnderTest.actionPerformed(null);
        verify(dialog).dispose();
    }
}
