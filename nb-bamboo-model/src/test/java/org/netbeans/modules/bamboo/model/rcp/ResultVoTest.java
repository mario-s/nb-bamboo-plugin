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
package org.netbeans.modules.bamboo.model.rcp;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Mario Schroeder
 */
class ResultVoTest {
    
    private static final String FOO = "foo";
    
    private ResultVo classUnderTest;
    
    private LocalDateTime now;
    
    @BeforeEach
    void setUp() {
        classUnderTest = new ResultVo(FOO);
        now = LocalDateTime.now();
    }

    /**
     * Test of getBuildStartedTime method, of class ResultVo.
     */
    @Test
    void testGetBuildStartedTime() {
        classUnderTest.setBuildCompletedTime(now);
        LocalDateTime result = classUnderTest.getBuildCompletedTime();
        assertEquals(result, now);
    }
    
    @Test
    void testEquals_AddChanges_ShouldBeEqual() {
        ResultVo other = new ResultVo(FOO);
        other.setChanges(singletonList(new ChangeVo()));
        assertTrue(classUnderTest.equals(other));
    }
}
