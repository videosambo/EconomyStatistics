package fi.videosambo.economystatistic.webserver.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {

    public String encode(byte[] byteArray, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(byteArray);
        byte[] digestedBytes = messageDigest.digest();
        return new BigInteger(1, digestedBytes).toString(16);
    }
}
