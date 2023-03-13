package com.example.demo2.repository;

import com.example.demo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByMobileNumber(String mobileNumber);
    List<User> findAllByEmail(String email);
    User findByEmail(String email);

}
