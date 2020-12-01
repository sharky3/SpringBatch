package com.tryagain.batch.rest.repo;

import com.tryagain.batch.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
