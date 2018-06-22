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
package org.netbeans.modules.bamboo.client.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Mario Schroeder
 */
public class StringUtilTest {

    /**
     * Test of split method, of class StringUtil.
     */
    @Test
    public void testSplit() {
        String prop = "a/b";
        Collection<String> result = StringUtil.split(prop);
        assertEquals(2, result.size());
    }

    /**
     * Test of join method, of class StringUtil.
     */
    @Test
    public void testJoin() {
        List<String> pieces = new ArrayList<>();
        pieces.add("a");
        pieces.add("b");
        String expResult = "a/b";
        String result = StringUtil.join(pieces);
        assertEquals(expResult, result);
    }

}
