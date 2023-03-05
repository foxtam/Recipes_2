package net.foxtam.hyperskillorg.recipes.presentation;

import net.foxtam.hyperskillorg.recipes.business.UserService;
import net.foxtam.hyperskillorg.recipes.persistance.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping("/api/register")
    ResponseEntity<?> register(@Valid @RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        boolean registered = userService.registerUser(user);
        return new ResponseEntity<>(registered ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
