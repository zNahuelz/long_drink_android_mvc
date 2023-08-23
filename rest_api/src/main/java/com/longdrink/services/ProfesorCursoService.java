package com.longdrink.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longdrink.dao.IProfesorCursoDAO;
import com.longdrink.model.ProfesorCurso;

@Service
public class ProfesorCursoService {
  @Autowired
  private IProfesorCursoDAO dao;

  public ProfesorCurso obtenerProfesorCurso(int id_curso){
    return dao.findByidcurso(id_curso);
  }
}
