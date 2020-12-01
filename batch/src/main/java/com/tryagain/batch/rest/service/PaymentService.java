package com.tryagain.batch.rest.service;

import com.tryagain.batch.model.Payment;
import com.tryagain.batch.rest.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    //potential OutOfMemoryError if the file is too big
    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentRepository.findAll());
    }

    public Optional<Payment> getPayment(Long id) {
        return paymentRepository.findById(id);
    }

    @Autowired
    private ConfigurableApplicationContext appContext;

    public void stopApp(int exitCode) {
        SpringApplication.exit(appContext, () -> exitCode);
    }

}
