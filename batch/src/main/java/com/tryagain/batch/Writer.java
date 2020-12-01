package com.tryagain.batch;
import com.tryagain.batch.model.Payment;
import com.tryagain.batch.rest.repo.PaymentRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Writer implements ItemWriter<Payment> {

    private final PaymentRepository repo;

    public Writer(PaymentRepository rep) {
        this.repo = rep;
    }

    @Override
    public void write(List<? extends Payment> payments) {
        repo.saveAll(payments);
    }
}
