package org.netbeans.modules.bamboo.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.VersionInfo;
import org.netbeans.modules.bamboo.mock.MockRestClient;
import org.netbeans.modules.bamboo.model.ProjectVo;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.openide.util.Lookup.getDefault;

/**
 *
 * @author spindizzy
 */
@RunWith(MockitoJUnitRunner.class)
public class BambooInstanceFactoryTest {
    @Mock
    private BambooServiceAccessable delegate;
    @Mock
    private InstanceValues values;
    
    private BambooInstanceFactory classUnderTest;
    
    @Before
    public void setUp() {
        MockRestClient client = (MockRestClient) getDefault().lookup(BambooServiceAccessable.class);
        client.setDelegate(delegate);
        classUnderTest = new BambooInstanceFactory();
    }
    
     /**
     * Test of create method, of class BambooInstanceFactory.
     */
    @Test
    public void testCreate_ExpectInstanceWithVersionInfo() {
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setBuildNumber(1);
        given(delegate.getVersionInfo(values)).willReturn(versionInfo);
        BambooInstance result = classUnderTest.create(values);
        assertThat(result.getVersionInfo().getBuildNumber(), is(1));
    }

    /**
     * Test of create method, of class BambooInstanceFactory.
     */
    @Test
    public void testCreate_ExpectInstanceWithProject() {
        given(delegate.getProjects(values)).willReturn(singletonList(new ProjectVo()));
        BambooInstance result = classUnderTest.create(values);
        assertThat(result.getProjects().isEmpty(), is(false));
    }
    
}
