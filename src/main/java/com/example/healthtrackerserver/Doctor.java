package com.example.healthtrackerserver;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor extends User{
    @JdbcTypeCode(SqlTypes.JSON)
    List<Long> patientsIds;
}
