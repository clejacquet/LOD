package jp.kde.lod.jacquet.mediaselector.web.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Created by Clement on 07/05/2015.
 */
public class EncryptionUtils {
    private static final String PASSWORD = "kdelod";
    private static final BasicTextEncryptor ENCRYPTOR = new BasicTextEncryptor();;

    static {
        ENCRYPTOR.setPassword(PASSWORD);
    }

    public static String encryptPassword(String password) {
        return ENCRYPTOR.encrypt(password);
    }

    private EncryptionUtils() {

    }
}
