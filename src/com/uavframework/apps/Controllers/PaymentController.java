package com.uavframework.apps.Controllers;

import com.uavframework.apps.Services.Payments.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uav.Common.DataModels.BaseTransaction;
import uav.Common.DataModels.Transactions.BaseCheck;
import uav.Common.DataModels.Transactions.BaseClientBill;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentsService paymentServices;

    @PostMapping(path = "/moneyTransfer")
    public ResponseEntity<?> transferMoney(@Valid @RequestBody BaseTransaction transaction) {
        BaseClientBill newBill = this.paymentServices.transferMoney(transaction);
        if(newBill != null) {
            return ResponseEntity.ok(newBill);
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }

    @PostMapping(path = "/cashCheck")
    public ResponseEntity<?> cashCheck(@Valid @RequestBody BaseCheck check) {
        BaseClientBill newBill = this.paymentServices.cashCheck(check);
        if(newBill != null) {
            return ResponseEntity.ok(newBill);
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }

    @PostMapping(path = "/{billId}/refresh")
    public ResponseEntity<?> refreshBill(@PathVariable String billId) {
        BaseClientBill newBill = this.paymentServices.refreshBill(billId);
        if(newBill != null) {
            return ResponseEntity.ok(newBill);
        }
        return ResponseEntity.badRequest().body("Invalid data request");
    }
}