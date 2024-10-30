package com.web.cartalk.core.config;

import com.web.cartalk.user.Role;
import com.web.cartalk.user.User;
import com.web.cartalk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Profile("local")
@Configuration
@RequiredArgsConstructor
public class InitDummyData implements ApplicationRunner {

    private final UserRepository userRepository;

    // password test1234
    @Override
    public void run(ApplicationArguments args) {
        userRepository.save(new User(
                null, "test1234", "{bcrypt}$2a$10$DCwSLZiW0LHJRNyPxAt49eHMH2h8G5fS8VGelgboJ7Ayp8zXrMGLO", "test", false,
                LocalDate.now().minusYears(20L), "test1234@naver.com", Role.ROLE_USER));
    }
}
