package com.uavframework.apps.Services.Payments;

import uav.Common.DataModels.BaseTransaction;
import uav.Common.DataModels.Transactions.BaseCheck;
import uav.Common.DataModels.Transactions.BaseClientBill;

public interface PaymentsService {
    BaseClientBill transferMoney(BaseTransaction transaction);
    BaseClientBill cashCheck(BaseCheck check);
    BaseClientBill refreshBill(String billId);
}
