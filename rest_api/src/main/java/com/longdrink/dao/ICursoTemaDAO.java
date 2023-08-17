package com.longdrink.dao;

import org.springframework.data.repository.CrudRepository;

import com.longdrink.model.CursoTema;
import java.util.Optional;


public interface ICursoTemaDAO extends CrudRepository<CursoTema, Integer>{
  Optional<CursoTema> findById_curso(int id_curso);
}
