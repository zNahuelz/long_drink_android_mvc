package com.longdrink.services;

import com.longdrink.dao.IUsuarioAlumnoDAO;
import com.longdrink.model.UsuarioAlumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAlumnoService {
    @Autowired
    private IUsuarioAlumnoDAO usrAlumDAO;
    public boolean buscarPorIDUsuario(int id_usuario){
        return usrAlumDAO.findByidUsuario(id_usuario).isPresent();
    }

    public UsuarioAlumno obtenerPorIDUsuario(int id_usuario){
        try{
            return usrAlumDAO.findByidUsuario(id_usuario).get();
        }
        catch(Exception ex){ return new UsuarioAlumno(); }
    }

    public UsuarioAlumno obtenerPorIDAlumno(int id_alumno){
        try{
            return usrAlumDAO.findByidAlumno(id_alumno).get();
        }
        catch(Exception ex){ return new UsuarioAlumno(); }
    }
}