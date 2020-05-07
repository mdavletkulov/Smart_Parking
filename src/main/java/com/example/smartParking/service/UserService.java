package com.example.smartParking.service;

import com.example.smartParking.domain.Role;
import com.example.smartParking.domain.User;
import com.example.smartParking.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found!");

        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }
        user.setEnabled(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getUsername())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Добро пожаловать в систему Умная парковка. Пожалуйста, пройдите по ссылке для окончания регистрации" +
                            ":http://localhost:8080/activate/%s",
                    user.getFullName(),
                    user.getActivationCode()
            );

            mailSender.send(user.getUsername(), "Activation code", message);

        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setEnabled(true);
        userRepo.save(user);

        return true;
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<Object> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

//        user.setRole(Role.USER);

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.setRole(Role.valueOf(key));
            }
        }

        userRepo.save(user);
        refreshSession();
    }

    public void updateProfile(User user, String password, String email,
                              String firstName, String secondName, String middleName) {
        String userEmail = user.getUsername();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) user.setUsername(email);

        if (!StringUtils.isEmpty(email))
            user.setActivationCode(UUID.randomUUID().toString());

        if (isEmailChanged) sendMessage(user);

        if (!StringUtils.isEmpty(password))
            user.setPassword(passwordEncoder.encode(password));

        if (!StringUtils.isEmpty(firstName)) user.setFirstName(firstName);
        if (!StringUtils.isEmpty(secondName)) user.setSecondName(secondName);
        user.setMiddleName(middleName);

        userRepo.save(user);
        refreshSession();
    }

    private void refreshSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(Role.ADMIN);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

}
