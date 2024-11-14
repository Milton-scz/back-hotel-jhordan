package com.jhordan.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jhordan.Entity.Habitacion;

public interface HabitacionRepository extends MongoRepository<Habitacion, Long> {
    Habitacion findByNumeroHabitacion(int numeroHabitacion);
    Boolean existsByNumeroHabitacion(int numeroHabitacion);
}