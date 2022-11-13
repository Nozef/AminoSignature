package amino.util;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

    //you can use java.util.HexFormat if use java.version >= 17
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static byte[] concatenate(byte[] a, byte[] b) {
        byte[] c = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public String signature(String data) throws GeneralSecurityException {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(hexStringToByteArray("f8e7a61ac3f725941e3ac7cae2d688be97f30b93"), "HmacSHA1"));
        byte[] a = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(concatenate(hexStringToByteArray("42"), a));
    }
}
