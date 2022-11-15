package com.chubov.twetter_clone.service;

import com.chubov.twetter_clone.domain.Role;
import com.chubov.twetter_clone.domain.User;
import com.chubov.twetter_clone.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        User user = userRepo.findByUsername(username);
        //Invalid username
        if (user==null) {
            throw new BadCredentialsException("User not found");
        }
        //Invalid password or something else
        else if (user!=null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return user;
    }


    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to my web project - Twitter clone. \n" +
                            "Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(), user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public void updateProfile(User user, String newPassword, String newEmail) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = (newEmail != null && !newEmail.equals(userEmail) ||
                (userEmail != null && !userEmail.equals(newEmail)));
        if (isEmailChanged) {
            user.setEmail(newEmail);
            if (!StringUtils.isEmpty(newEmail)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!StringUtils.isEmpty(newPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepo.save(user);
        if (isEmailChanged) {
            sendMessage(user);
        }
    }
}
