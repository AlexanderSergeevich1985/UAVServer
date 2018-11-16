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

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.spark.mllib.linalg.DenseVector;
import scala.tools.jline_embedded.internal.Nullable;
import uav.BaseOperation.NeuralNetwork.IAutoEncoder;

import javax.annotation.Nonnull;
import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import java.nio.charset.StandardCharsets;

public class AuthenticationHelper {
    static public boolean publicKeyChecker(final PublicKey checkedPubKey, final String validatorEncryptedStr,
                                           final String sharedSecret, final String knownMessage,
                                           final String algorithm) throws Exception { //described in http://nauko-sfera.ru/wp-content/uploads/2017/08/Iyul-skie-nauchny-e-chteniya-2017..pdf - 69 page
        if(checkedPubKey == null || validatorEncryptedStr == null || validatorEncryptedStr.trim().isEmpty() || sharedSecret == null || sharedSecret.trim().isEmpty()) return false;
        final String trueValidatorStr = HashGeneratorHelper.getCertKeyValidatorStr(sharedSecret, knownMessage, algorithm);
        String trueValidatorEncryptedStr = encrypt(trueValidatorStr, checkedPubKey, null);
        return trueValidatorEncryptedStr.equals(validatorEncryptedStr);
    }

    static public String encrypt(@Nonnull final String plainText, @Nonnull final PublicKey publicKey, final String algorithm) throws Exception {
        Cipher cipher = null;
        if(algorithm == null || algorithm.trim().isEmpty()) {
            cipher = Cipher.getInstance("RSA");
        }
        else {
            cipher = Cipher.getInstance(algorithm);
        }
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    static public String decrypt(@Nonnull String cipherText, @Nonnull PrivateKey privateKey, final String algorithm) throws Exception {
        Cipher cipher = null;
        if(algorithm == null || algorithm.trim().isEmpty()) {
            cipher = Cipher.getInstance("RSA");
        }
        else {
            cipher = Cipher.getInstance(algorithm);
        }
        byte[] bytes = Base64.getDecoder().decode(cipherText);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
    }

    static public String generateEncodedSecret(@Nonnull final IAutoEncoder<Integer> autoEncoder, @Nonnull final RandomGenerator randGen) {
        final int size = autoEncoder.getLayerInputSize(0);
        Integer[] secret = new Integer[size];
        for(int i = 0; i < size; ++i) {
            secret[i] = HashGeneratorHelper.getRandLimited(randGen, 33, 126);
        }
        double[] encodedSecret = autoEncoder.encodeVector(secret).toArray();
        StringBuilder  builder = new StringBuilder(encodedSecret.length);
        for(int i = 0; i < encodedSecret.length; ++i) {
            if(i != 0) builder.append(" ");
            builder.append(encodedSecret[i]);
        }
        return builder.toString();
    }

    @Nullable
    static public String decodeSecretStr(@Nonnull final IAutoEncoder<Integer> autoEncoder, final String encodedSecretStr) {
        if(encodedSecretStr == null || encodedSecretStr.isEmpty()) return null;
        String[] encodedSecretArray = encodedSecretStr.split(" ");
        double[] encodedSecret = new double[encodedSecretArray.length];
        for(int i = 0; i < encodedSecretArray.length; ++i) {
            encodedSecret[i] = Double.parseDouble(encodedSecretArray[i]);
        }
        Integer[] decodedSecret = autoEncoder.decodeVector(new DenseVector(encodedSecret));
        StringBuilder  builder = new StringBuilder(encodedSecret.length);
        for(int i = 0; i < decodedSecret.length; ++i) {
            char symbol = Character.forDigit(decodedSecret[i], 10);
            builder.append(symbol);
        }
        return builder.toString();
    }
}
