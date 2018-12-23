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

import uav.Common.DataModels.BaseEntity;

import javax.annotation.Nonnull;
import javax.jdo.annotations.Embedded;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT_BILLS")
public class BaseClientBill extends BaseEntity {
    @Embedded
    ClientBillDesc clientBillDesc;

    @Lob
    @Column(name = "bill_signature", nullable = false)
    private byte[] billSignature;

    public BaseClientBill() {}

    @Nonnull
    public ClientBillDesc getClientBillDesc() {
        return clientBillDesc;
    }

    public void setClientBillDesc(@Nonnull ClientBillDesc clientBillDesc) {
        this.clientBillDesc = clientBillDesc;
    }

    @Nonnull
    public byte[] getBillSignature() {
        return billSignature;
    }

    public void setBillSignature(@Nonnull byte[] billSignature) {
        this.billSignature = billSignature;
    }
}