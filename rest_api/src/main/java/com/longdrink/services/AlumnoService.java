package com.longdrink.services;

import com.longdrink.dao.IAlumnoDAO;
import com.longdrink.model.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
    @Autowired
    private IAlumnoDAO alumnoDAO;

    public List<Alumno> listarAlumnos(){
        return (List<Alumno>)alumnoDAO.findAll();
    }

    public List<Alumno> listarAlumnosActivos(){
        List<Alumno> listaAlum = listarAlumnos();
        List<Alumno> retorno = new ArrayList<Alumno>();
        for(Alumno a : listaAlum){
            if (a.getActivo() == 1){
                retorno.add(a);
            }
        }
        return retorno;
    }


    public Alumno guardarAlumno(Alumno a){
        a.setId_alumno(0);
        Optional<Alumno> alumDNI = alumnoDAO.findBydni(a.getDni());
        Optional<Alumno> alumEmail = alumnoDAO.findByemail(a.getEmail());
        boolean vacio = a.isEmpty();
        if(!alumDNI.isPresent() && !alumEmail.isPresent() && !vacio){
            return alumnoDAO.save(a);
        }
        return null;
    }

    public Alumno actualizarAlumno(Alumno a){
        return alumnoDAO.save(a);
    }

    public boolean eliminarAlumnoID(int id){
        try{
            Alumno alum = alumnoDAO.findById(id).get();
            if(alum.getId_alumno() != 0 ){
                alum.setActivo(0);
                alumnoDAO.save(alum);
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;
    }

    public boolean buscarPorID(int id){
        Optional<Alumno> alum = alumnoDAO.findById(id);
        return alum.isPresent();
    }

    public Alumno obtenerAlumno(int id){
        return alumnoDAO.findById(id).get();
    }

    public Alumno obtenerAlumDNI(String dni){
        return alumnoDAO.findBydni(dni).get();
    }

    public Alumno obtenerAlumEmail(String email){return alumnoDAO.findByemail(email).get();}

    public boolean buscarPorDNI(String dni){ return alumnoDAO.findBydni(dni).isPresent(); }
    public boolean buscarPorEmail(String email){return alumnoDAO.findByemail(email).isPresent();}
}
