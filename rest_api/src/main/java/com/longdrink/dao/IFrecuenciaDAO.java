package com.longdrink.dao;

import com.longdrink.model.Frecuencia;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.List;

public interface IFrecuenciaDAO extends CrudRepository<Frecuencia, Integer> {
    Optional<List<Frecuencia>> findByactivo(int activo);
}
