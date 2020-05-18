package com.example.smartParking.service;

import com.example.smartParking.model.domain.Role;
import com.example.smartParking.model.domain.User;
import com.example.smartParking.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) throw new IllegalArgumentException("Пользователь не найден");
        if (!user.isEnabled()) throw new IllegalArgumentException("Пользователь не активирован.");
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
        if (!user.getUsername().isEmpty()) {
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

    public boolean updateProfile(User user, String email,
                                 String firstName, String secondName, String middleName, Model model) {
        String userEmail = user.getUsername();
        boolean correctChanging = true;
        if (email == null || email.isBlank()) {
            model.addAttribute("usernameError", "Поле электронной почты было пустым");
            correctChanging = false;
        }
        if (firstName == null || firstName.isBlank()) {
            model.addAttribute("firstNameError", "Поле имени было пустым");
            correctChanging = false;
        }
        if (secondName == null || secondName.isBlank()) {
            model.addAttribute("secondNameError", "Поле фамилии было пустым");
            correctChanging = false;
        }

        if (correctChanging) {
            boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                    (userEmail != null && !userEmail.equals(email));
            if (isEmailChanged) user.setUsername(email);
//            if (email != null && !email.isBlank())
//                user.setActivationCode(UUID.randomUUID().toString());
//            if (isEmailChanged) sendMessage(user);
            user.setFirstName(firstName);
            user.setSecondName(secondName);
            user.setMiddleName(middleName);
            userRepo.save(user);
            refreshSession();
        }
        return correctChanging;
    }

    public boolean updatePassword(User user, String password, String password2, Model model) {
        if (!password.isBlank() && !password2.isBlank() && password.equals(password2)) {
            user.setPassword(passwordEncoder.encode(password));
            userRepo.save(user);
            refreshSession();
            model.addAttribute("message", "Пароль успешно изменен!");
            return true;
        } else {
            if (StringUtils.isEmpty(password)) model.addAttribute("passwordError", "Поле пароля пустое");
            if (StringUtils.isEmpty(password2))
                model.addAttribute("password2Error", "Поле подтверждения пароля пустое");
            if (!password.equals(password2)) model.addAttribute("passwordError", "Пароли не совпадают");
            return false;
        }
    }

    private void refreshSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(Role.ADMIN);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

}
