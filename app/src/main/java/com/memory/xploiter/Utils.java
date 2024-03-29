package com.memory.xploiter;

import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import static android.os.Environment.DIRECTORY_PICTURES;

class Utils {

    private static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }



    static String readStream(InputStream in) throws IOException {


        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = "";


            while ((line = reader.readLine()) != null) {
                response.append(line);


            }
        }
        return response.toString();
    }

    static String SHA256(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(md.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    static void clearCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteFilesInDir(dir);
        } catch (Exception ignored) {}
    }

    private static void deleteFilesInDir(File dir) {
        for (File file: dir.listFiles()) {
            if (file.isDirectory()) {
                deleteFilesInDir(file);
            }
            file.delete();
        }
    }


    static String profileDecrypt(String data, String sign) {
        char[] key = sign.toCharArray();
        char[] out = fromBase64String(data).toCharArray();
        for(int i = 0; i < out.length;i++){
            out[i] = (char)(key[i % key.length] ^ out[i]);
        }
        return new String(out);
    }
    private static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }



    public static String encrypt(String plainText, byte[] keyBytes) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey(keyBytes));
        return Utils.toBase64(encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
    }

   public static boolean verify(String plainText, String signature, byte[] keyBytes) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(getPublicKey(keyBytes));
        publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
        return publicSignature.verify(Utils.fromBase64(signature));
    }


    static String toBase64(String s) {
        return Base64.encodeToString(s.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }

    static String toBase64(byte[] s) {
        return Base64.encodeToString(s, Base64.NO_WRAP);
    }

    static byte[] fromBase64(String s) {
        return Base64.decode(s, Base64.NO_WRAP);
    }

    static String fromBase64String(String s) {
        return new String(Base64.decode(s, Base64.NO_WRAP), StandardCharsets.UTF_8);
    }
}
