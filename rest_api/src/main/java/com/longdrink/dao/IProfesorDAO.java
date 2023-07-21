package com.longdrink.dao;

import com.longdrink.model.Profesor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IProfesorDAO extends CrudRepository<Profesor,Integer> {
    List<Profesor> findAllByactivo(int activo);
    Optional<Profesor> findBydni(String dni);
    Optional<Profesor> findByemail(String email);
}
