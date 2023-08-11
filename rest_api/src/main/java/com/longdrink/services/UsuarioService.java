package com.longdrink.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.longdrink.dao.IUsuarioDAO;

import com.longdrink.model.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    public List<Usuario> listarUsuarios(){
        return (List<Usuario>)usuarioDAO.findAll();
    }

    public List<Usuario> listarUsuariosActivos(){
        List<Usuario> listaAlum = listarUsuarios();
        List<Usuario> retorno = new ArrayList<Usuario>();
        for(Usuario a : listaAlum){
            if (a.getActivo() == 1){
                retorno.add(a);
            }
        }
        return retorno;
    }


    public Usuario guardarUsuario(Usuario a){
        a.setId_usuario(0);

        Optional<Usuario> usuario = usuarioDAO.findBynombreusuario(a.getNombre_usuario());
        boolean vacio = a.isEmpty();
        if(!usuario.isPresent() && !vacio){
            return usuarioDAO.save(a);
        }
        return null;
    }

    public Usuario actualizarUsuario(Usuario a){
        return usuarioDAO.save(a);
    }

    public boolean eliminarUsuarioID(int id){
        try{
            Usuario usu = usuarioDAO.findByidusuario(id).get();
            if(usu.getId_usuario() != 0 ){
                usu.setActivo(0);
                usuarioDAO.save(usu);
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;
    }

    public boolean buscarPorID(int id){
        Optional<Usuario> usu = usuarioDAO.findByidusuario(id);
        return usu.isPresent();
    }

    public Usuario obtenerUsuario(int id){
        try{
            return usuarioDAO.findByidusuario(id).get();
        }
        catch(Exception ex){ return new Usuario(); }
    }

    public Usuario obtenerNombreUsuario(String nombre_usuario){
        try{
            return usuarioDAO.findBynombreusuario(nombre_usuario).get();
        }
        catch(Exception ex){
            return null;
        }

    }
}