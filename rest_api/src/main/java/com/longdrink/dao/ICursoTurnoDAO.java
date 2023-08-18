package com.longdrink.dao;


import org.springframework.data.repository.CrudRepository;

import com.longdrink.model.CursoTurno;
import java.util.List;
import java.util.Optional;


public interface ICursoTurnoDAO extends CrudRepository<CursoTurno, Integer>{
  Optional<CursoTurno> findByidCurso(int id_curso);
}
