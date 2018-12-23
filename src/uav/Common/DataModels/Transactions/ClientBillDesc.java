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
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
public class ClientBillDesc implements Serializable {
    @Column(name = "issuer_id")
    @Length(max = 500)
    private String issuerId;

    @Column(name = "uuid", columnDefinition = "BINARY(32)", nullable = false)
    private UUID uuid;

    @Lob
    @Column(name = "public_key", nullable = false)
    private byte[] publicKey;

    @Lob
    @Column(name = "server_public_key", nullable = false)
    private byte[] serverPublicKey;

    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance;

    @Column(name = "shared_secret")
    @Length(max = 500)
    private String encodedSharedSecret;

    public ClientBillDesc() {}

    @Nullable
    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(@Nullable String issuerId) {
        this.issuerId = issuerId;
    }

    @Nonnull
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(@Nonnull UUID uuid) {
        this.uuid = uuid;
    }

    @Nonnull
    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(@Nonnull byte[] publicKey) {
        this.publicKey = publicKey;
    }

    @Nonnull
    public byte[] getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(@Nonnull byte[] serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    @Nonnull
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(@Nonnull BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Nullable
    public String getEncodedSharedSecret() {
        return encodedSharedSecret;
    }

    public void setEncodedSharedSecret(@Nullable String encodedSharedSecret) {
        this.encodedSharedSecret = encodedSharedSecret;
    }
}