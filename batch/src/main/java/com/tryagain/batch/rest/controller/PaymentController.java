package com.tryagain.batch.rest.controller;

import com.tryagain.batch.model.Payment;
import com.tryagain.batch.rest.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;


@RestController
public class PaymentController {

    @Autowired
    private PaymentService payment;

    @RequestMapping("/payments")
    public List<Payment> getAllPayments() {
        return payment.getAllPayments();
    }

    @RequestMapping("/payments/{id}")
    public Optional<Payment> getPayment(@PathVariable Long id) {
        return payment.getPayment(id);
    }

    @RequestMapping("/exit")
    public void stopApp() {
        payment.stopApp(0);
    }

}
