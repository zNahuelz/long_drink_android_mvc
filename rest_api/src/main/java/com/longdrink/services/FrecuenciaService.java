package com.longdrink.services;

import com.longdrink.dao.IFrecuenciaDAO;
import com.longdrink.model.Frecuencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FrecuenciaService {
    @Autowired
    private IFrecuenciaDAO frecuenciaDAO;

    public List<Frecuencia> listarFrecuencias(){
        return (List<Frecuencia>) frecuenciaDAO.findAll();
    }

    public List<Frecuencia> listarFrecuenciasAct(){
        List<Frecuencia> listaFrec = listarFrecuencias();
        List<Frecuencia> retorno = new ArrayList<Frecuencia>();
        for (Frecuencia f : listaFrec){
            if(f.getActivo() == 1){
                retorno.add(f);
            }
        }
        return retorno;
    }

    public Frecuencia guardarFrecuencia(Frecuencia f){
        f.setId_frecuencia(0);
        if(f.getActivo() != 0 || f.getActivo() != 1){ f.setActivo(0); }
        boolean vacio = f.isEmpty();
        if (!vacio){
            return frecuenciaDAO.save(f);
        }
        return null;
    }

    public Frecuencia actualizarFrecuencia(Frecuencia f){
        return frecuenciaDAO.save(f);
    }

    public boolean eliminarFrecuenciaID(int id){
        try{
            Frecuencia f = frecuenciaDAO.findById(id).get();
            if(f.getActivo() != 0){
                f.setActivo(0);
                frecuenciaDAO.save(f);
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;
    }

    public boolean buscarPorID(int id){
        Optional<Frecuencia> f = frecuenciaDAO.findById(id);
        return f.isPresent();
    }

    public Frecuencia obtenerFrecuencia(int id){
        try{
            return frecuenciaDAO.findById(id).get();
        }
        catch(Exception ex){
            return new Frecuencia();
        }

    }
}
