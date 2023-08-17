package com.longdrink.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.longdrink.dao.ICursoFrecuenciaDAO;
import com.longdrink.model.CursoFrecuencia;

public class CursoFrecuenciaService {
  
  @Autowired
  ICursoFrecuenciaDAO usrFrecDAO;

  public boolean buscarPorIDCurso(int id_curso){
    return usrFrecDAO.findById_curso(id_curso).isPresent();
  }

  public CursoFrecuencia obtenerPorIdCurso(int id_curso){
    try{
      return usrFrecDAO.findById_curso(id_curso).get();
    }
    catch(Exception ex){
      return new CursoFrecuencia();
    }
  }
}
