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
 * @author Mario Schroeder
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
