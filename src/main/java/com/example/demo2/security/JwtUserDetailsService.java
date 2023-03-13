package com.example.demo2.security;

import com.example.demo2.exception.NoRecordExistsException;
import com.example.demo2.exception.RecordAlreadyExistsException;
import com.example.demo2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<com.example.demo2.model.User> userList = userRepository.findAllByEmail(username);
        if (userList.size() > 1) {
            throw new RecordAlreadyExistsException("Multiple Users Already there with email " + username);
        } else if (userList.size() == 0) {
            throw new NoRecordExistsException("User not registered with email " + username);
        }
        com.example.demo2.model.User u = userList.get(0);

        if (u!=null) {
            return new User(u.getEmail(),
                    u.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with mail: " + username);
        }
    }
}
