package org.netbeans.modules.bamboo.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author spindizzy
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
