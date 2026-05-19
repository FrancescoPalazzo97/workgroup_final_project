package com.final_project.workgroup_final_project;

import com.final_project.workgroup_final_project.models.Role;
import com.final_project.workgroup_final_project.models.User;
import com.final_project.workgroup_final_project.repos.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(new User(
                "admin@test.com",
                passwordEncoder.encode("admin123"),
                "Admin User",
                Role.ADMIN
        ));

        userRepository.save(new User(
                "mario@test.com",
                passwordEncoder.encode("user123"),
                "Mario Rossi",
                Role.USER
        ));

        userRepository.save(new User(
                "luigi@test.com",
                passwordEncoder.encode("user123"),
                "Luigi Verdi",
                Role.USER
        ));
    }
}
