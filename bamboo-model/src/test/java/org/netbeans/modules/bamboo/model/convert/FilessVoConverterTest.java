package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;

import static java.util.Collections.singletonList;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.FileVo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.netbeans.modules.bamboo.model.rest.File;
import org.netbeans.modules.bamboo.model.rest.Files;

/**
 *
 * @author spindizzy
 */
public class FilessVoConverterTest {

    private static final String FOO = "foo";

    private CollectionVoConverter classUnderTest;

    private Files source;

    @Before
    public void setUp() {
        
        classUnderTest = new CollectionVoConverter(new FileVoConverter());
        source = new Files();
    }

    @Test
    public void testConvert_WithChanges_ExpectChangesVo() {
        File file = new File();
        source.setFiles(singletonList(file));
        Collection<FileVo> result = classUnderTest.convert(source);
        assertThat(result.isEmpty(), is(false));
    }
    
     @Test
    public void testConvert_NullChanges_ExpectEmpty() {
        Collection<FileVo> result = classUnderTest.convert(null);
        assertThat(result.isEmpty(), is(true));
    }
}
