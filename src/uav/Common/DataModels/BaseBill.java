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

import org.hibernate.validator.constraints.Length;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ISSUED_BILLS")
public class BaseBill extends BaseEntity {
    @Column(columnDefinition = "BINARY(32)", nullable = false)
    private UUID uuid;

    @Column(name = "shared_secret")
    @Length(max = 500)
    private String sharedSecret;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "indentation", length = 256, nullable = false)
    private String indentation;  //output of cryptographic function over bill data; the cryptographic function stored by bill issued service for further bill validation

    @Nonnull
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(@Nonnull final UUID uuid) {
        this.uuid = uuid;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(final String sharedSecret) {
        this.sharedSecret = sharedSecret;
    }

    @Nonnull
    public BigDecimal getAmount() {
        return amount;
    }

    @Nonnull
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    @Nonnull
    public String getIndentation() {
        return indentation;
    }

    public void setIndentation(@Nonnull final String indentation) {
        this.indentation = indentation;
    }
}
