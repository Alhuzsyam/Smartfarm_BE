package com.smartfarming.iot.Service;


import com.smartfarming.iot.Data.Model.User;
import com.smartfarming.iot.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import org.springframework.security.core.userdetails.*;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                // Collections.emptyList()
                Collections.singletonList(() -> user.getRoles())
        );

   
    }
    public boolean updateApiKey(String username, String newApiKey) {
        int rowsAffected = userRepository.updateApiKeyByUsername(username, newApiKey);
        return rowsAffected > 0;
    }
}






