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

import org.netbeans.modules.bamboo.model.rcp.FileVo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.netbeans.modules.bamboo.model.rest.File;
import org.netbeans.modules.bamboo.model.rest.Files;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mario Schroeder
 */
class FilessVoConverterTest {

    private static final String FOO = "foo";

    private CollectionVoConverter classUnderTest;

    private Files source;

    @BeforeEach
    void setUp() {
        
        classUnderTest = new CollectionVoConverter(new FileVoConverter());
        source = new Files();
    }

    @Test
    void testConvert_WithChanges_ExpectChangesVo() {
        File file = new File();
        source.setFiles(singletonList(file));
        Collection<FileVo> result = classUnderTest.convert(source);
        assertFalse(result.isEmpty());
    }
    
    @Test
    void testConvert_NullChanges_ExpectEmpty() {
        Collection<FileVo> result = classUnderTest.convert(null);
        assertTrue(result.isEmpty());
    }
}
