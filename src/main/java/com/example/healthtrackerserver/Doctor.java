package com.example.healthtrackerserver;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor extends User{
    public List<Long> patientsIds = new ArrayList<>();
}
