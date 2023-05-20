package com.example.healthtrackerserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorsRepository extends CrudRepository<Doctor, Long> {
    @Query(value = "SELECT d FROM Doctor d")
    List<Doctor> fetchAll();

    @Query(value = "SELECT d FROM Doctor d WHERE username = ?1")
    Optional<Doctor> findByUsername(String username);
}
