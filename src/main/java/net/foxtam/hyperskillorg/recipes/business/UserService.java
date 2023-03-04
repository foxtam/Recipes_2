package net.foxtam.hyperskillorg.recipes.business;

import net.foxtam.hyperskillorg.recipes.persistance.User;
import net.foxtam.hyperskillorg.recipes.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private UserRepository repository;

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
}
