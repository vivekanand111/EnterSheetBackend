package com.example.demo2.repository;

import com.example.demo2.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Integer> {
    Otp findByEmail(String email);
    Otp findTopByEmailOrderByExpiryTimeDesc(String email);

    void deleteAllByEmail(String email);
}
