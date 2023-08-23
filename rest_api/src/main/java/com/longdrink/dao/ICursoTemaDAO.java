package com.longdrink.dao;

import org.springframework.data.repository.CrudRepository;

import com.longdrink.model.CursoTema;
import java.util.Optional;
import java.util.List;



public interface ICursoTemaDAO extends CrudRepository<CursoTema, Integer>{

  List<CursoTema> findByidCurso(int id_curso);
}
