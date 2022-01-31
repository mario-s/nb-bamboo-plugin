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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rest.Project;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author Mario Schroeder
 */
class ProjectVoConverterTest {
    private static final String FOO = "foo";
    
    private ProjectVoConverter classUnderTest;
    
    private Project source;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new ProjectVoConverter(FOO);
        source = new Project();
        source.setKey(FOO);
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    void testConvert() {
        ProjectVo result = classUnderTest.convert(source);
        assertEquals(result.getKey(), FOO);
    }

}
