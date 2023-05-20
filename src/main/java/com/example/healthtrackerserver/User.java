package com.example.healthtrackerserver;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String username;
    public String password;

    public int stepGoal;
    public int waterGoal;
    public int caloriesGoal;

    public long doctorId;
}
