package org.netbeans.modules.bamboo.model;

import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * This is a simple de/encrypter.
 *
 * @author spindizzy
 */
final class Encrypter {
    private static final String PASS = "org.netbeans.modules.bamboo";
    private final Cipher ecipher;
    private final Cipher dcipher;

    private Encrypter() {
        try {
            DESKeySpec keySpec = new DESKeySpec(PASS.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);
            this.ecipher = Cipher.getInstance("DES");
            this.dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                UnsupportedEncodingException | InvalidKeySpecException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static Encrypter getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    public String encrypt(char [] chars) {
        return encrypt(new String(chars));
    }

    public String encrypt(String str) {
        String result = "";

        try {
            // Encode the string into bytes using utf-8

            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            result = Base64.getEncoder().encodeToString(enc);
        } catch (IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException ex) {
            throw new IllegalArgumentException(ex);
        }

        return result;
    }

    public String decrypt(final String str) {
        String result = "";

        try {
            // Decode base64 to get bytes
            byte[] dec = Base64.getDecoder().decode(str);

            byte[] utf8 = dcipher.doFinal(dec);

            result = new String(utf8, "UTF8");
        } catch (IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException ex) {
            throw new IllegalArgumentException(ex);
        }

        // Decode using utf-8
        return result;
    }

    private static class SingletonHelper {
        private static final Encrypter INSTANCE = new Encrypter();
    }
}
