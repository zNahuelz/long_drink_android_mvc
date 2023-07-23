package com.longdrink.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.longdrink.model.Tema;

public interface ITemaDAO extends CrudRepository<Tema, Integer>{
	Optional<List<Tema>> findByactivo(int activo);
	
	Optional<Tema> findBynombre(String nombre);
    
}
