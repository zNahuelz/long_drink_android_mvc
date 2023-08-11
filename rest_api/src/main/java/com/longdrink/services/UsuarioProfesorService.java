package com.longdrink.services;

import com.longdrink.dao.IUsuarioProfesorDAO;
import com.longdrink.model.UsuarioProfesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioProfesorService {
    @Autowired
    private IUsuarioProfesorDAO usrProfDAO;

    public boolean buscarPorIDUsuario(int id_usuario){
        return usrProfDAO.findByidUsuario(id_usuario).isPresent();
    }

    public UsuarioProfesor obtenerPorIDUsuario(int id_usuario){
        try{
            return usrProfDAO.findByidUsuario(id_usuario).get();
        }
        catch(Exception ex){ return new UsuarioProfesor(); }
    }
}
