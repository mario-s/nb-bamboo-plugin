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


import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.Link;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author Mario Schroeder
 */
class IssueVoConverterTest {
    private static final String FOO = "foo";
    
    private IssueVoConverter classUnderTest;
    
    private Issue source;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new IssueVoConverter();
        source = new Issue();
        source.setKey(FOO);
        Link link = new Link();
        link.setHref(FOO);
        source.setLink(link);
    }

    /**
     * Test of convert method, of class IssueVoConverter.
     */
    @Test
    void testConvert_ExpectNotNull() {
        IssueVo result = classUnderTest.convert(source);
        assertNotNull(result);
    }
    
}
