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
package uav.Common.DataModels;

import uav.Common.DataModels.Transactions.TransactionDesc;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
@Table(name = "TRANSACTIONS")
public class BaseTransaction extends BaseEntity {
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "known_msg")
    private String knownMsg;

    @Embedded
    private TransactionDesc transactionDescriptor;

    @Lob
    @Column(name = "transaction_signature", nullable = false)
    private byte[] transactionSignature;

    public BaseTransaction() {}

    @Nullable
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(@Nullable Long transactionId) {
        this.transactionId = transactionId;
    }

    @Nullable
    public String getKnownMsg() {
        return knownMsg;
    }

    public void setKnownMsg(@Nullable String knownMsg) {
        this.knownMsg = knownMsg;
    }

    @Nonnull
    public TransactionDesc getTransactionDescriptor() {
        return transactionDescriptor;
    }

    public void setTransactionDescriptor(@Nonnull TransactionDesc transactionDescriptor) {
        this.transactionDescriptor = transactionDescriptor;
    }

    @Nonnull
    public byte[] getTransactionSignature() {
        return transactionSignature;
    }

    public void setTransactionSignature(@Nonnull byte[] transactionSignature) {
        this.transactionSignature = transactionSignature;
    }
}