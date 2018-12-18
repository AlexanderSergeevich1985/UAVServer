package uav.Common.Evaluator;

import uav.Common.DataModels.Transactions.CheckDesc;
import uav.Utils.CryptoHelper;

import javax.annotation.Nonnull;
import java.security.PrivateKey;
import java.security.Signature;

public class BaseTransactionChecker {
    static public byte[] signCheck(@Nonnull final CheckDesc checkDesc, @Nonnull final PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(CryptoHelper.serializeObject(checkDesc));
        return privateSignature.sign();
    }

    static public boolean verifyCheck(@Nonnull final CheckDesc checkDesc, @Nonnull final byte[] signature) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(CryptoHelper.bytesToPublicKey(checkDesc.getPayerPublicKey()));
        publicSignature.update(CryptoHelper.serializeObject(checkDesc));
        return publicSignature.verify(signature);
    }
}
