package com.example.service;


import com.example.entity.UserAccount;
import com.example.repo.UserAccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserAccountService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

//    public UserAccount findByUsername(String username) {
//        return userAccountRepository.findByUsername(username);
//    }

    public UserAccount findByUserEmail(String username) {
        return userAccountRepository.findByUserEmail(username);
    }

    public void saveUserAccount(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User("karthik","karthik",new ArrayList<>());
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUserEmail(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(userAccount.getUserEmail(), userAccount.getPassword(),
                Collections.emptyList());
    }
//
//    public UserAccount registerUser(UserAccount userAccount) {
//        // Additional logic for validating and saving the user account
//        return userAccountRepository.save(userAccount);
//    }
}
