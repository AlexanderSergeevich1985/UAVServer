package com.uavframework.apps.Controllers;

import com.uavframework.apps.Services.PaymentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uav.Common.DataModels.BaseBill;
import uav.Common.DataModels.BaseTransaction;
import uav.Common.DataModels.Transactions.BaseCheck;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentServices paymentServices;

    @PostMapping(path = "/moneyTransfer")
    public ResponseEntity<?> transferMoney(@Valid @RequestBody BaseTransaction transaction) {
        BaseBill newBill = this.paymentServices.transferMoney(transaction);
        if(newBill != null) {
            return ResponseEntity.ok(newBill);
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }

    @PostMapping(path = "/cashCheck")
    public ResponseEntity<?> cashCheck(@Valid @RequestBody BaseCheck check) {
        BaseBill newBill = this.paymentServices.cashCheck(check);
        if(newBill != null) {
            return ResponseEntity.ok(newBill);
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }

    @PostMapping(path = "/{billId}/refresh")
    public ResponseEntity<?> refreshBill(@PathVariable String billId) {
        BaseBill newBill = this.paymentServices.refreshBill(billId);
        if(newBill != null) {
            return ResponseEntity.ok(newBill);
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }
}