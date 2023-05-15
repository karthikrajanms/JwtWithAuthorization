package com.example.repo;

import com.example.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

//    UserAccount findByUsername(String username);

    UserAccount findByUserEmail(String userEmail);
}
