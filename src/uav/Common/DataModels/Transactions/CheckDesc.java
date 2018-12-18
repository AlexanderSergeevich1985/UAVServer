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
package uav.Common.DataModels.Transactions;

import org.hibernate.validator.constraints.Length;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Embeddable
public class CheckDesc implements Serializable {
    @Column(name = "sign_time", nullable = false)
    private Timestamp signTime;

    @Column(name = "payer_id", nullable = false)
    @Length(max = 500)
    private String payerId;

    @Column(name = "payee_id", nullable = false)
    @Length(max = 500)
    private String payeeId;

    @Column(name = "transfer_amount", nullable = false)
    private BigDecimal transferAmount;

    @Lob
    @Column(name = "payer_public_key", nullable = false)
    private byte[] payerPublicKey;

    public CheckDesc() {}

    @Nonnull
    public Timestamp getSignTime() {
        return signTime;
    }

    public void setSignTime(@Nonnull Timestamp signTime) {
        this.signTime = signTime;
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
    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(@Nonnull BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Nonnull
    public byte[] getPayerPublicKey() {
        return payerPublicKey;
    }

    public void setPayerPublicKey(@Nonnull byte[] payerPublicKey) {
        this.payerPublicKey = payerPublicKey;
    }
}