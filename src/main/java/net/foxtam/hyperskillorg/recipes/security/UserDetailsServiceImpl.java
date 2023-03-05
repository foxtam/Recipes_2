package net.foxtam.hyperskillorg.recipes.security;

import net.foxtam.hyperskillorg.recipes.business.UserService;
import net.foxtam.hyperskillorg.recipes.persistance.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionUser = userService.getUserByEmail(username);
        if (optionUser.isEmpty()) {
            throw new UsernameNotFoundException("Not found: " + username);
        }
        return new UserDetailsImpl(optionUser.get());
    }
}
