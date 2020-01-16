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
import org.netbeans.modules.bamboo.model.rcp.ResultVo;
import org.netbeans.modules.bamboo.model.rest.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 *
 * @author Mario Schroeder
 */
class ResultVoConverterTest {

    private static final String FOO = "foo";

    private ResultVoConverter classUnderTest;

    private Result source;

    @BeforeEach
    void setUp() {
        classUnderTest = new ResultVoConverter();
        source = new Result();
        source.setKey(FOO);
        source.setBuildStartedTime("2016-12-02T07:43:02.000+01:00");
        source.setBuildCompletedTime("2016-12-02T07:43:02.000+01:00");
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    void testConvert_ExpectKey() {
        ResultVo result = classUnderTest.convert(source);
        assertEquals(result.getKey(), FOO);
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    void testConvert_ExpectCompletionTime() {
        ResultVo result = classUnderTest.convert(source);
        assertNotNull(result.getBuildCompletedTime());
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    void testConvert_ExpectStartTime() {
        ResultVo result = classUnderTest.convert(source);
        assertNotNull(result.getBuildStartedTime());
    }

    /**
     * Test of convert method, of class VoConverter.
     */
    @Test
    void testConvert_NoOffset_ExpectFormatedStartTime() {
        source.setBuildStartedTime("2016-12-02T07:43:02.000");
        ResultVo result = classUnderTest.convert(source);
        assertNotNull(result.getBuildStartedTime());
    }

}
