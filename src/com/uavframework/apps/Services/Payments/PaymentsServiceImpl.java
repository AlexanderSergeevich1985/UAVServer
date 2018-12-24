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
