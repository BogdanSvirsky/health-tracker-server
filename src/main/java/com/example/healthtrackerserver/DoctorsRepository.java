package com.example.healthtrackerserver;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorsRepository extends CrudRepository<Doctor, Long> {
    Doctor findByUsername(String username);
}
