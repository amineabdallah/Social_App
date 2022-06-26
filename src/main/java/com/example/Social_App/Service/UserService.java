package com.example.Social_App.Service;

import com.example.Social_App.Model.ConfirmationToken;
import com.example.Social_App.Model.Role;
import com.example.Social_App.Model.User;
import com.example.Social_App.Repositiry.RoleRepository;
import com.example.Social_App.Repositiry.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    public UserService(final UserRepository userRepository, final RoleRepository roleRepository, final PasswordEncoder passwordEncoder, final ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("user not found");
            throw new UsernameNotFoundException("no data found");
        } else {
            log.info("user found");
        }
        final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final Role role = roleRepository.findByName("MEMBER");
        final Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);


    }

    public String sign(final User user) {

        final User userExists = userRepository.findByEmail(user.getEmail());

        if (userExists != null) {
            throw new IllegalStateException("email already taken");
        }
        user.setLocked(false);
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final Role role = roleRepository.findByName("MEMBER");
        final Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        final String token = UUID.randomUUID().toString();

        final ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveToken(confirmationToken);

        return token;
    }

    public User findById(final Long id_User) {
        return userRepository.findById(id_User).orElseThrow(() -> new RuntimeException("No data found!"));
    }

    public User findById(final String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(final Long id_user, final User user) {
        final User oldUser = userRepository.findById(id_user).orElseThrow(() -> new RuntimeException("No data found!"));

        oldUser.setEmail(user.getEmail());
        oldUser.setUsername(user.getUsername());
        oldUser.setFirstname(user.getFirstname());
        oldUser.setLastname(user.getLastname());
        oldUser.setPassword(user.getPassword());

        return userRepository.save(oldUser);
    }

    public void deleteUser(final Long id_user) {
        final User oldUser = userRepository.findById(id_user).orElseThrow(() -> new RuntimeException("No data found!"));
        userRepository.delete(oldUser);
    }

    public User addRoleToUser(final String username, final String RoleName) {
        final User user = userRepository.findByUsername(username);
        final Role role = roleRepository.findByName(RoleName);
        user.getRoles().add(role);
        return userRepository.save(user);

    }

    public int enableAppUser(final String email) {
        return userRepository.enableUser(email);
    }


}
