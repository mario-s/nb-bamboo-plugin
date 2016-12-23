package org.netbeans.modules.bamboo.convert;

import java.util.Collection;

import static java.util.Collections.singletonList;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Change;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.rest.Changes;

/**
 *
 * @author spindizzy
 */
public class ChangesVoConverterTest {

    private static final String FOO = "foo";

    private ChangesVoConverter classUnderTest;

    private Changes source;

    @Before
    public void setUp() {
        classUnderTest = new ChangesVoConverter();
        source = new Changes();
    }

    @Test
    public void testConvert_WithChanges_ExpectChangesVo() {
        Change change = new Change();
        source.setChanges(singletonList(change));
        Collection<ChangeVo> result = classUnderTest.convert(source);
        assertThat(result.isEmpty(), is(false));
    }
    
     @Test
    public void testConvert_NullChanges_ExpectEmpty() {
        Collection<ChangeVo> result = classUnderTest.convert(null);
        assertThat(result.isEmpty(), is(true));
    }
}
