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

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Change;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class ChangeVoConverterTest {

    private static final String FOO = "foo";

    private ChangeVoConverter classUnderTest;

    private Change source;

    @Before
    public void setUp() {
        classUnderTest = new ChangeVoConverter();
        source = new Change();
        source.setChangesetId(FOO);
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert_ExpectChangesetId() {
        ChangeVo result = classUnderTest.convert(source);
        assertThat(result.getChangesetId(), equalTo(FOO));
    }
    
    
    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    public void testConvert_Date_ExpectDate() {
        source.setDate("2016-12-02T07:43:02.000");
        ChangeVo result = classUnderTest.convert(source);
        assertThat(result.getDate(), notNullValue());
    }

}
