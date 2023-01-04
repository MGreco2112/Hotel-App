package com.hotel.Services;

import com.hotel.Security.Services.UserDetailsImpl;
import com.hotel.models.auth.User;
import com.hotel.payloads.request.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*
    Common Methods:

    -getById();
    -authenticateUser();
    -getCurrentUser();
    -getLoggedInUser();
     */

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<User> currentUser = userRepository.findById(userDetails.getId());

        if (currentUser.isEmpty()) {
            return null;
        }

        return currentUser.get();
    }


}
