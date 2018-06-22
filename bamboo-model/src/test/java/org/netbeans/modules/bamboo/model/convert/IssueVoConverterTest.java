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

import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.Link;

/**
 *
 * @author Mario Schroeder
 */
public class IssueVoConverterTest {
    private static final String FOO = "foo";
    
    private IssueVoConverter classUnderTest;
    
    private Issue source;
    
    @Before
    public void setUp() {
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
    public void testConvert_ExpectNotNull() {
        IssueVo result = classUnderTest.convert(source);
        assertThat(result, notNullValue());
    }
    
}
