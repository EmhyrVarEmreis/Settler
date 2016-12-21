package pl.morecraft.dev.settler.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MessageDigestComponent {

    private static Logger log = LoggerFactory.getLogger(MessageDigestComponent.class);

    public String md5(byte[] bytes) {
        try {
            return getHex(MessageDigest.getInstance("MD5").digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException: MD5", e);
        }
        return null;
    }

    private String getHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            if ((0xff & b) < 0x10) {
                hexString.append("0").append(Integer.toHexString((0xFF & b)));
            } else {
                hexString.append(Integer.toHexString(0xFF & b));
            }
        }
        return hexString.toString();
    }

}
