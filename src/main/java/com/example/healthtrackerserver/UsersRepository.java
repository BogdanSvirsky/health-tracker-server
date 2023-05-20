package com.example.healthtrackerserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u")
    List<User> fetchAll();

    @Query("SELECT u FROM User u WHERE username = ?1")
    Optional<User> findByUsername(String username);
}
