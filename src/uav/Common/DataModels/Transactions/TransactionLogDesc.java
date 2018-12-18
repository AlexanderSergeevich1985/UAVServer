package uav.Common.DataModels.Transactions;

import org.hibernate.validator.constraints.Length;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Embeddable
public class TransactionLogDesc {
    @Column(name = "completion_time", nullable = false)
    private Timestamp completionTime;

    @Column(name = "payer_id", nullable = false)
    @Length(max = 500)
    private String payerId;

    @Column(name = "payee_id", nullable = false)
    @Length(max = 500)
    private String payeeId;

    @Column(name = "payer_balance", nullable = false)
    private BigDecimal payerBalance;

    @Column(name = "payee_balance", nullable = false)
    private BigDecimal payeeBalance;

    @Column(name = "transfer_amount", nullable = false)
    private BigDecimal transferAmount;

    @Lob
    @Column(name = "public_key", nullable = false)
    private byte[] publicKey;

    @Lob
    @Column(name = "private_key", nullable = false)
    private byte[] privateKey;

    public TransactionLogDesc() {}

    @Nonnull
    public Timestamp getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(@Nonnull Timestamp completionTime) {
        this.completionTime = completionTime;
    }

    @Nonnull
    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(@Nonnull String payerId) {
        this.payerId = payerId;
    }

    @Nonnull
    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(@Nonnull String payeeId) {
        this.payeeId = payeeId;
    }

    @Nonnull
    public BigDecimal getPayerBalance() {
        return payerBalance;
    }

    public void setPayerBalance(@Nonnull BigDecimal payerBalance) {
        this.payerBalance = payerBalance;
    }

    @Nonnull
    public BigDecimal getPayeeBalance() {
        return payeeBalance;
    }

    public void setPayeeBalance(@Nonnull BigDecimal payeeBalance) {
        this.payeeBalance = payeeBalance;
    }

    @Nonnull
    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(@Nonnull BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Nonnull
    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(@Nonnull byte[] publicKey) {
        this.publicKey = publicKey;
    }

    @Nonnull
    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(@Nonnull byte[] privateKey) {
        this.privateKey = privateKey;
    }
}