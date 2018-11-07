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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashGeneratorHelper {
    @Nullable
    static public String getCertKeyValidatorStr(final String sharedSecret, final String knownMessage,
                                                final String algorithm) throws NoSuchAlgorithmException { //described in http://nauko-sfera.ru/wp-content/uploads/2017/08/Iyul-skie-nauchny-e-chteniya-2017..pdf - 69 page
        String mixedMessage = mixTwoString(sharedSecret, knownMessage);
        if(mixedMessage == null) return null;
        return new String(byteToString(getHash(mixedMessage, algorithm)));
    }

    static public RandomGenerator getRandomGenerator(final int seed) {
        return RandomGeneratorFactory.createRandomGenerator(new Random(seed));
    }

    static public int getRandLimited(@Nonnull final RandomGenerator randGen, final int min, final int max) {
        return randGen.nextInt((max - min) + 1) + min;
    }

    static public String getRandString(@Nonnull final RandomGenerator randGen, final int size) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < size; ++i) {
            char symbol = Character.forDigit(getRandLimited(randGen, 33, 126), 10);
            builder.append(symbol);
        }
        return builder.toString();
    }

    static public String getRandSequence(@Nonnull final RandomGenerator randGen, final int size) throws NoSuchAlgorithmException {
        String randStr = getRandString(randGen, size);
        byte[] salt = getSalt(randGen, getRandLimited(randGen, 5, size));
        return new String(Hex.encodeHex(getHashWithSalt(randStr, "MD5", salt)));
    }

    static public byte[] getSalt(@Nonnull final RandomGenerator randGen, final int size) {
        byte[] salt = new byte[size];
        randGen.nextBytes(salt);
        return salt;
    }

    static public byte[] stringToByte(@Nonnull final String input) {
        if(Base64.isBase64(input)) {
            return Base64.decodeBase64(input);
        }
        else {
            return Base64.encodeBase64(input.getBytes());
        }
    }

    static public String byteToString(@Nonnull final byte[] input) {
        return Base64.encodeBase64String(input);
    }

    @Nullable
    static public byte[] getHash(final String input, final String algorithm) throws NoSuchAlgorithmException {
        if(algorithm == null || algorithm.trim().length() == 0 || input == null) return null;
        MessageDigest md = MessageDigest.getInstance(algorithm);
        if(md == null) return null;
        md.reset();
        return md.digest(stringToByte(input));
    }

    @Nullable
    static public byte[] getHashWithSalt(final String input, final String algorithm, byte[] salt) throws NoSuchAlgorithmException {
        if(algorithm == null || algorithm.trim().length() == 0 || input == null) return null;
        MessageDigest md = MessageDigest.getInstance(algorithm);
        if(md == null) return null;
        md.reset();
        md.update(salt);
        return md.digest(stringToByte(input));
    }

    @Nullable
    static public String mixTwoString(final String first, final String second) {
        if(first == null || first.trim().length() == 0 || second == null || second.trim().length() == 0) return null;
        final int min = Math.min(first.length(), second.length());
        final StringBuilder builder = new StringBuilder(first.length() + second.length());
        for(int i = 0; i < min; ++i) {
            builder.append(first.charAt(i));
            builder.append(second.charAt(i));
        }
        if(first.length() > second.length()) {
            builder.append(first.substring(min));
        }
        else {
            builder.append(second.substring(min));
        }
        return builder.toString();
    }
}
