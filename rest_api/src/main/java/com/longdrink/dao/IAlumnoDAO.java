package com.longdrink.dao;

import com.longdrink.model.Alumno;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IAlumnoDAO extends CrudRepository<Alumno,Integer> {
    Optional<Alumno> findBynombre(String nombre);
    Optional<Alumno> findBydni(String dni);
    Optional<List<Alumno>> findByactivo(int activo);
    Optional<Alumno> findByemail(String email);
}
