package com.example.healthtrackerserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    User findById(long id);

    User findByUsername(String username);

    List<User> getPatientsByDoctorId(long doctorId);

    List<User> getAll();
}
