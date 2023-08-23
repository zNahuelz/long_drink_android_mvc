package com.longdrink.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longdrink.dao.ICursoTemaDAO;
import com.longdrink.model.CursoTema;

@Service
public class CursoTemaService {
  
  @Autowired
  private ICursoTemaDAO dao;

  public List<CursoTema> getALlCursoTemasByID(int id_curso){
    return dao.findByidCurso(id_curso);
  }
}
