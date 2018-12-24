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
package com.uavframework.apps.Services.Payments;

import org.springframework.stereotype.Service;
import uav.Common.DataModels.BaseTransaction;
import uav.Common.DataModels.Transactions.BaseCheck;
import uav.Common.DataModels.Transactions.BaseClientBill;
import uav.Common.DataModels.Transactions.BaseServerBill;
import uav.Common.Evaluator.BaseTransactionChecker;
import uav.Common.Repository.ServerBillsRepository;

import javax.annotation.Nonnull;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PaymentsServiceImpl implements PaymentsService {
    static private final Logger logger;

    static {
        logger = Logger.getLogger(PaymentsServiceImpl.class.getName());
        logger.setLevel(Level.INFO);
    }

    private final ServerBillsRepository repository;

    public PaymentsServiceImpl(@Nonnull ServerBillsRepository repository) {
        this.repository = repository;
    }

    public BaseClientBill transferMoney(final BaseTransaction transaction) {
        BaseServerBill payerBill = this.repository.findOne(transaction.getTransactionDescriptor().getPayerId());
        BaseServerBill payeeBill = this.repository.findOne(transaction.getTransactionDescriptor().getPayeeId());
        if(payerBill == null || payeeBill == null) return null;
        BaseClientBill result = null;

        try {
            if(BaseTransactionChecker.verifyTransaction(transaction, payerBill)) {
                result = BaseTransactionChecker.transferMoney(payerBill, payeeBill);
            }
        }
        catch(final Exception ex) {
            if(logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Exception occurred: ", ex);
            }
        }
        return result;

    }

    public BaseClientBill cashCheck(final BaseCheck check) {
        if(BaseTransactionChecker.verifyCheck(check)) {
            BaseServerBill payerBill = this.repository.findOne(check.getCheckDesc().getPayerId());
            BaseServerBill payeeBill = this.repository.findOne(check.getCheckDesc().getPayeeId());
            if(payerBill == null || payeeBill == null) return null;
            BaseClientBill result = null;

            try {
                result = BaseTransactionChecker.transferMoney(payerBill, payeeBill);
            }
            catch(final Exception ex) {
                if(logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, "Exception occurred: ", ex);
                }
            }
            return result;
        }
    }

    public BaseClientBill refreshBill(String billId) {
        BaseServerBill clientBill = this.repository.findOne(billId);
        if(clientBill == null) return null;

        BaseClientBill result = null;

        try {
            result = BaseTransactionChecker.generateClientBill(clientBill);
        }
        catch(final Exception ex) {
            if(logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, "Exception occurred: ", ex);
            }
        }
        return result;
    }
}
