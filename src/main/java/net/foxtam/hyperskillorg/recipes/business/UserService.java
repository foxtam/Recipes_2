package net.foxtam.hyperskillorg.recipes.business;

import net.foxtam.hyperskillorg.recipes.persistance.User;
import net.foxtam.hyperskillorg.recipes.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean registerUser(User user) {
        boolean exists = repository.existsByEmail(user.getEmail());
        if (!exists) {
            repository.save(user);
        }
        return !exists;
    }

    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}
