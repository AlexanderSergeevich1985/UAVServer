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
package uav.Common.Evaluator;

import uav.Common.DataModels.BaseTransaction;
import uav.Common.DataModels.Transactions.*;
import uav.Utils.CryptoHelper;
import uav.Utils.DateTimeHelper;

import javax.annotation.Nonnull;
import javax.crypto.Cipher;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Arrays;

public class BaseTransactionChecker {
    static public String issuerId = "Internet Banking Open Source Organization";

    static public byte[] signCheck(@Nonnull final CheckDesc checkDesc, @Nonnull final PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, CryptoHelper.bytesToPublicKey(checkDesc.getPayeePublicKey()));
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(cipher.doFinal(CryptoHelper.serializeObject(checkDesc)));
        return privateSignature.sign();
    }

    static public boolean verifyCheck(@Nonnull final CheckDesc checkDesc, @Nonnull final byte[] signature) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, CryptoHelper.bytesToPublicKey(checkDesc.getPayeePublicKey()));
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(CryptoHelper.bytesToPublicKey(checkDesc.getPayerPublicKey()));
        publicSignature.update(cipher.doFinal(CryptoHelper.serializeObject(checkDesc)));
        return publicSignature.verify(signature);
    }

    static public boolean verifyCheck(@Nonnull final BaseCheck check, @Nonnull final BaseServerBill payerBill, @Nonnull final BaseServerBill payeeBill) throws Exception {
        if(!Arrays.equals(check.getCheckDesc().getPayerPublicKey(), payerBill.getServerBillDesc().getOwnerPublicKey())) return false;
        if(!Arrays.equals(check.getCheckDesc().getPayeePublicKey(), payeeBill.getServerBillDesc().getOwnerPublicKey())) return false;
        return verifyCheck(check.getCheckDesc(), check.getCheckSignature());
    }

    static public boolean verifyTransaction(@Nonnull final TransactionDesc transactionDesc, @Nonnull final byte[] signature) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(CryptoHelper.bytesToPublicKey(transactionDesc.getPayerPublicKey()));
        publicSignature.update(CryptoHelper.serializeObject(transactionDesc));
        return publicSignature.verify(signature);
    }

    static public boolean verifyTransaction(@Nonnull final BaseTransaction transaction, @Nonnull final BaseServerBill payerBill) throws Exception {
        if(!Arrays.equals(transaction.getTransactionDescriptor().getPayerPublicKey(), payerBill.getServerBillDesc().getOwnerPublicKey())) return false;
        return verifyTransaction(transaction.getTransactionDescriptor(), transaction.getTransactionSignature());
    }

    static public BaseClientBill generateClientBill(@Nonnull final BaseServerBill clientBill) throws Exception {
        ClientBillDesc clientBillDesc = new ClientBillDesc();
        clientBillDesc.setAccountBalance(clientBill.getServerBillDesc().getAccountBalance());
        clientBillDesc.setUuid(clientBill.getServerBillDesc().getUuid());
        clientBillDesc.setIssuerId(issuerId);
        clientBillDesc.setServerPublicKey(clientBill.getServerBillDesc().getPublicKey());
        clientBillDesc.setEncodedSharedSecret(clientBill.getServerBillDesc().getSharedSecret());
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, CryptoHelper.bytesToPublicKey(clientBillDesc.getPublicKey()));
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(CryptoHelper.bytesToPrivateKey(clientBill.getServerBillDesc().getPrivateKey()));
        privateSignature.update(cipher.doFinal(CryptoHelper.serializeObject(clientBillDesc)));
        BaseClientBill result = new BaseClientBill();
        clientBillDesc.setEncodedSharedSecret("");
        result.setCreationTime(DateTimeHelper.zdtToTimestamp(DateTimeHelper.getCurrentTimeWithTimeZone("UTC")));
        result.setClientBillDesc(clientBillDesc);
        result.setBillSignature(privateSignature.sign());
        return result;
    }

    static public BaseClientBill transferMoney(@Nonnull final BaseServerBill payerBill, @Nonnull final BaseServerBill payeeBill, final BigDecimal amount) throws Exception {
        BigDecimal payerBalance = payerBill.getServerBillDesc().getAccountBalance();
        BigDecimal payeeBalance = payeeBill.getServerBillDesc().getAccountBalance();
        if(payerBalance.compareTo(amount) >= 0) {
            payerBill.getServerBillDesc().setAccountBalance(payerBalance.subtract(amount));
            payeeBill.getServerBillDesc().setAccountBalance(payeeBalance.add(amount));
        }
        return generateClientBill(payerBill);
    }
}
