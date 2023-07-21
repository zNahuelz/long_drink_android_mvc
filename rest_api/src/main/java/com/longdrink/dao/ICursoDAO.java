package com.longdrink.dao;

import com.longdrink.model.Curso;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ICursoDAO extends CrudRepository<Curso,Integer> {
    Optional<List<Curso>> findByactivo(int activo);

    Optional<Curso> findBynombre(String nombre);

}
