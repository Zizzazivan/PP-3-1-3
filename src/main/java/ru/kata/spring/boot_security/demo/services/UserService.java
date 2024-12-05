package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.configs.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));
         return  new org.springframework.security.core.userdetails.User(
                 user.getUsername(),
                 user.getPassword(),
                 user.getAuthorities());
    }


}
