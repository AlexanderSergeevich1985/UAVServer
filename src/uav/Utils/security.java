package uav.Document;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Utils {
    static public KeyPair generate_RSA() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            keyPair = kpg.generateKeyPair();
            PublicKey pub = keyPair.getPublic();
            Key pvt = keyPair.getPrivate();
        }
        catch(NoSuchAlgorithmException nsaex) {}
        return keyPair;
    }
    static public void writeKey(String fileName, Key key) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            out.write(key.getEncoded());
            out.close();
        }
        catch(FileNotFoundException fnfex) {}
        catch(IOException ioex) {}
    }
    static public void writePublicKeyAsText(String fileName, PublicKey key) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            out.write(key.getEncoded());
            out.close();
        }
        catch(FileNotFoundException fnfex) {}
        catch(IOException ioex) {}
    }
    static public void writePrivateKeyAsText(String fileName, PrivateKey key) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            out.write(key.getEncoded());
            out.close();
        }
        catch(FileNotFoundException fnfex) {}
        catch(IOException ioex) {}
    }
    static public PrivateKey loadPrivatekey(String fileName) {
        Path path = Paths.get(fileName);
        PrivateKey key = null;
        try {
            byte[] bytes = Files.readAllBytes(path);
            PKCS8EncodedKeySpec pkcs8ks = new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            key = keyf.generatePrivate(pkcs8ks);
        }
        catch(IOException ioex) {}
        catch(NoSuchAlgorithmException nsaex) {}
        catch(InvalidKeySpecException ikspex) {}
        return key;
    }
    static public PublicKey loadPublickey(String fileName) {
        Path path = Paths.get(fileName);
        PublicKey key = null;
        try {
            byte[] bytes = Files.readAllBytes(path);
            X509EncodedKeySpec x509eks = new X509EncodedKeySpec(bytes);
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            key = keyf.generatePublic(x509eks);
        }
        catch(IOException ioex) {}
        catch(NoSuchAlgorithmException nsaex) {}
        catch(InvalidKeySpecException ikspex) {}
        return key;
    }
}
