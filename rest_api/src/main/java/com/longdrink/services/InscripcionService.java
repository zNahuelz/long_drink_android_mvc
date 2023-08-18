package com.longdrink.services;

import com.longdrink.dao.IInscripcionDAO;
import com.longdrink.model.Inscripcion;

import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {
    @Autowired
    private IInscripcionDAO inscripcionDAO;

    public List<Inscripcion> listarInscripcion(){
        return (List<Inscripcion>) inscripcionDAO.findAll();
    }

    public List<Inscripcion> listarActivos(int activo){
        return inscripcionDAO.findAllByactivo(activo);
    }

    public List<Inscripcion> listarPorIDAlum(int id_alum){
        return inscripcionDAO.findAllByidalumno(id_alum);
    }

    public List<Inscripcion> listarPorTerminado(int t){
        return inscripcionDAO.findAllByterminado(t);
    }

    public Inscripcion guardarInscripcion(Inscripcion i){
        boolean vacio = i.isEmpty();
        if(!vacio){
            return inscripcionDAO.save(i);
        }
        return null;
    }
    @Transactional
    public int guardarIns(Inscripcion i){
        Query query = em.createQuery("INSERT INTO Inscripcion(id_alumno,id_curso,fecha_inicio_curso,fecha_final_curso,fecha_inscripcion,terminado,activo) values(:ida,:idc,:fic,:ffc,:fiic,:t,:a)");
        query.setParameter("ida",i.getId_alumno());
        query.setParameter("idc",i.getId_curso());
        query.setParameter("fic",i.getFecha_inicio_curso());
        query.setParameter("ffc", i.getFecha_final_curso());
        query.setParameter("fiic", i.getFecha_inscripcion());
        query.setParameter("t", i.getTerminado());
        query.setParameter("a",i.getActivo());
        return query.executeUpdate();
    }

    public Inscripcion actualizarInscripcion(Inscripcion i){
        return inscripcionDAO.save(i);
    }

    public boolean terminarInscripcionIDAlum(int id_alum){
        try{
            Inscripcion i = inscripcionDAO.findAllByidalumno(id_alum).get(id_alum); //TODO : Check....
            if(i.getTerminado() != 1){
                i.setTerminado(1);
                inscripcionDAO.save(i);
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;
    }

    public Optional<Inscripcion> buscarPorIDAlum(int id_alum){
        return Optional.ofNullable(inscripcionDAO.findAllByidalumno(id_alum).get(id_alum)); //TODO : Check.....
    }

    public Optional<Inscripcion> buscarPorActivo(int activo){
        return Optional.ofNullable(inscripcionDAO.findAllByactivo(activo).get(activo)); //TODO : Check....
    }

    public Inscripcion obtenerPorIDAlum(int id_alum){
        return inscripcionDAO.findByidalumno(id_alum);
    }

}
