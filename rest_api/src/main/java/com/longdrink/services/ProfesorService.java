package com.longdrink.services;

import com.longdrink.dao.IProfesorDAO;
import com.longdrink.model.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    @Autowired
    private IProfesorDAO profesorDAO;

    public List<Profesor> listarProfesores(){
        return (List<Profesor>)profesorDAO.findAll();
    }

    public List<Profesor> listarProfesoresActivos(int activo){
        return profesorDAO.findAllByactivo(activo);
    }

    public Profesor guardarProfesor(Profesor p){
        p.setId_profesor(0);
        Optional<Profesor> profeDNI = profesorDAO.findBydni(p.getDni());
        Optional<Profesor> profeEmail = profesorDAO.findByemail(p.getEmail());
        boolean vacio = p.isEmpty();
        if(!vacio || !profeEmail.isPresent() || !profeDNI.isPresent()){
            return profesorDAO.save(p);
        }
        return null;
    }

    public Profesor actualizarProfesor(Profesor p){
        return profesorDAO.save(p);
    }

    public boolean eliminarProfesor(int id){
        try{
            Profesor p = profesorDAO.findById(id).get();
            if(p.getActivo() != 0){
                p.setActivo(0);
                profesorDAO.save(p);
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;
    }

    public boolean buscarPorID(int id){
        return profesorDAO.findById(id).isPresent();
    }

    public Profesor obtenerPorDNI(String dni){
        try{
            return profesorDAO.findBydni(dni).get();
        }                
        catch(Exception ex){
            return new Profesor();
        }    
    }

    public Profesor obtenerPorID(int id){
        try{
            return profesorDAO.findById(id).get();
        }
        catch(Exception ex){
            return new Profesor();
        }
    }
}
