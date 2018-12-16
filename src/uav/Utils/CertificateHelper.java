/**The MIT License (MIT)
 Copyright (c) 2018 by AleksanderSergeevich
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package uav.Utils;

import scala.tools.jline_embedded.internal.Nullable;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.*;

import java.security.*;
import java.security.cert.X509Certificate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CertificateHelper {
    public static final int RSA_KEY_SIZE = 2048;
    public static final String SIGNING_ALG = "SHA1WithRSA";

    private static final Logger logger = Logger.getLogger(CertificateHelper.class.getName());

    @Nullable
    static public X509Certificate getX509Certificate(X500Name x500Name, final long expiration) {
        X509Certificate x509Certificate = null;
        try {
            CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", SIGNING_ALG,null);
            if(x500Name == null) x500Name = new X500Name("CN=ROOT");
            ZonedDateTime zdtNow = DateTimeHelper.getCurrentTimeWithTimeZone("UTC");
            x509Certificate = certAndKeyGen.getSelfCertificate(x500Name, Date.from(zdtNow.toInstant()), expiration);
        }
        catch(final Exception ex){
            if(logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "NoSuchAlgorithmException occurred: ", ex);
            }
        }
        return x509Certificate;
    }

    @Nullable
    static public SecureRandom getSecureRandom() {
        SecureRandom srand = null;
        try {
            srand = SecureRandom.getInstanceStrong();
        }
        catch(final NoSuchAlgorithmException nsae) {
            if(logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "NoSuchAlgorithmException occurred: ", nsae);
            }
        }
        if(srand == null) srand = new SecureRandom();
        return srand;
    }

    @Nullable
    static public byte[] getSecureSeed(final int size) {
        SecureRandom srand = getSecureRandom();
        return (srand != null) ? srand.generateSeed(size) : null;
    }

    @Nullable
    static public KeyPair generateRSAKeys() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(RSA_KEY_SIZE, getSecureRandom());
            keyPair = kpg.generateKeyPair();
        }
        catch(final NoSuchAlgorithmException nsae) {
            if(logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "NoSuchAlgorithmException occurred: ", nsae);
            }
        }
        return keyPair;
    }

    static public String getUUIDString() {
        return UUID.randomUUID().toString();
    }
}
