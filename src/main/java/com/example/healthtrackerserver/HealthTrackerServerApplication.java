package com.example.healthtrackerserver;

import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class HealthTrackerServerApplication {
    @PersistenceContext
    EntityManager entityManager;
    private static ConfigurableApplicationContext context;
    private static UsersRepository usersRepository;
    private static DoctorsRepository doctorsRepository;

    public static void main(String[] args) {
        context = SpringApplication.run(HealthTrackerServerApplication.class, args);
        usersRepository = context.getBean(UsersRepository.class);
        doctorsRepository = context.getBean(DoctorsRepository.class);

        User user = new User();
        user.username = "admin";
        user.password = "admin";
        user.stepGoal = 999;
        user.waterGoal = 9999;
        user.caloriesGoal = 99999;
        usersRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.username = "bubnovski";
        doctor.password = "boobs";
        doctor.stepGoal = 666;
        doctor.waterGoal = 696;
        doctor.caloriesGoal = 99699;
        doctorsRepository.save(doctor);
        doctor.patientsIds.add(user.id);
        doctorsRepository.save(doctor);
        doctor.stepGoal = 1000;
        doctorsRepository.save(doctor);
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
        System.out.println(username + " " + password);
        if (username.isBlank() || password.isBlank())
            return null;
        else {
            Optional<User> user = usersRepository.findByUsername(username);
            System.out.println(user.get().stepGoal);
            if (user.isPresent() && Objects.equals(user.get().password, password)) {
                System.out.println("ok" + " " + user.get());
                return user.get();
            } else
                return null;
        }
    }

    @GetMapping("/users/getAll")
    public List<User> getUser() {
        return usersRepository.fetchAll();
    }

    @PostMapping("/users/add")
    public void addUser(@RequestBody User user) {
        if (user != null) {
            if (usersRepository.findByUsername(user.username).isEmpty()) // TODO: create an server error
                usersRepository.save(user);
        }
    }

    @GetMapping("/doctors/get")
    public User getDoctor(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {
        if (username.isBlank() || password.isBlank())
            return null;
        else {
            Optional<Doctor> doctor = doctorsRepository.findByUsername(username);
            if (doctor.isPresent() && Objects.equals(doctor.get().password, password)) {
                return doctor.get();
            } else
                return null;
        }
    }

    @GetMapping("/doctors/getAll")
    public List<Doctor> getAllDoctors() {
        return doctorsRepository.fetchAll();
    }

    //
    @PostMapping("/doctors/add")
    public void addDoctor(@RequestBody Doctor doctor) {
        if (doctor != null) {
            if (doctorsRepository.findByUsername(doctor.username).isEmpty()) // TODO: create an server error
                doctorsRepository.save(doctor);
        }
    }

    @PostMapping("/doctors/addPatient")
    public void addPatient(
            @RequestParam(value = "doctorId", defaultValue = "0") Long doctorsId,
            @RequestParam(value = "patientId", defaultValue = "0") Long patientsId
    ) {
        System.out.println(doctorsId + " " + patientsId);

        if (Objects.equals(doctorsId, patientsId) || patientsId == 0 || doctorsId == 0)
            return;

        Optional<Doctor> doctor = doctorsRepository.findById(doctorsId);
        Optional<User> patient = usersRepository.findById(patientsId);
        System.out.println(doctor.isPresent() + " " + patient.isPresent());

        if (doctor.isPresent() && patient.isPresent()) {
            doctor.get().patientsIds.add(patient.get().id);
            System.out.println("ok");
            doctorsRepository.save(doctor.get());
        }
    }
}
