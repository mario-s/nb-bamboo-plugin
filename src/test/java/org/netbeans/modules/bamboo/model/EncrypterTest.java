/*
 * Schuetze Consulting Informationssysteme AG, Berlin
 */
package org.netbeans.modules.bamboo.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * @author schroeder
 */
public class EncrypterTest {
    private Encrypter classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = Encrypter.getInstance();
    }

    /**
     * Test of encrypt method, of class Encrypter.
     */
    @Test
    public void testEncrypt() {
        String str = "";
        String expResult = "";
        String result = classUnderTest.encrypt(str);
        assertEquals(expResult, result);
    }

    /**
     * Test of decrypt method, of class Encrypter.
     */
    @Test
    public void testDecrypt() {
        String str = "test";
        String result = classUnderTest.decrypt(str);
        assertFalse(result.equals(str));
    }

    /**
     * Test of encrypt method, of class Encrypter.
     */
    @Test
    public void testEncrypt_And_Decrypt() throws Exception {
        String str = "test";
        String result = classUnderTest.decrypt(classUnderTest.encrypt(str));
        assertEquals(str, result);
    }
}
