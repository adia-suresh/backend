package com.klef.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.sdp.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>
{
}