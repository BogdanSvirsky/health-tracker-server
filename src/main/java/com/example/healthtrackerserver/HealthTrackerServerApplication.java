package com.example.healthtrackerserver;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class HealthTrackerServerApplication {
    private static ConfigurableApplicationContext context;
    private static UsersRepository usersRepository;
    private static DoctorsRepository doctorsRepository;

    public static void main(String[] args) {
        context = SpringApplication.run(HealthTrackerServerApplication.class, args);
        usersRepository = context.getBean(UsersRepository.class);
        doctorsRepository = context.getBean(DoctorsRepository.class);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping("/users/get")
    public User getUser(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {
        if (username.isBlank() || password.isBlank())
            return null;
        else {

            User user = usersRepository.findByUsername(username);

            if (user.password == password)
                return user;
            else
                return null;

        }
    }

    @GetMapping("/users/getAll")
    public List<User> getUser() {
        return new Session().createQuery(String.valueOf(User.class));
    }

    @PostMapping("/users/add")
    public void addUser(@RequestBody User user) {
        if (!user.username.isBlank() && !user.password.isBlank()) {
            if (usersRepository.findByUsername(user.username) == null)
                usersRepository.save(user);
        }
    }

    @GetMapping("/doctors/get")
    public Doctor getDoctor(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {
        if (username.isBlank() || password.isBlank())
            return null;
        else {
            Doctor doctor = doctorsRepository.findByUsername(username);
            if (doctor.password == password)
                return doctor;
            else
                return null;
        }
    }

    @PostMapping("/doctors/add")
    public void addDoctor(@RequestParam Doctor doctor) {
        if (doctorsRepository.findByUsername(doctor.username) == null)
            doctorsRepository.save(doctor);
    }
}
