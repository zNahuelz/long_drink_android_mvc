package com.longdrink.dao;

import com.longdrink.model.Turno;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ITurnoDAO extends CrudRepository<Turno,Integer> {

    Optional<List<Turno>> findByactivo(int activo);
}
