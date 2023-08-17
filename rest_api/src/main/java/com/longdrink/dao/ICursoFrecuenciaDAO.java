package com.longdrink.dao;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.longdrink.model.CursoFrecuencia;


public interface ICursoFrecuenciaDAO extends CrudRepository<CursoFrecuencia, Integer>{
  Optional<CursoFrecuencia> findById_curso(int id_curso);
 
}
