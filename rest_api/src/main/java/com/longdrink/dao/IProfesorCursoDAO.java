package com.longdrink.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.longdrink.model.ProfesorCurso;
import java.util.List;


public interface IProfesorCursoDAO extends CrudRepository<ProfesorCurso,Integer>{
  ProfesorCurso findByidcurso(int idcurso);
}
