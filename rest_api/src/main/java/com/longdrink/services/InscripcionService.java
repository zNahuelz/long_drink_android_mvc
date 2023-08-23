package com.longdrink.services;

import com.longdrink.dao.IInscripcionDAO;
import com.longdrink.model.Inscripcion;

import jakarta.persistence.EntityManager;
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

    @Autowired
    private EntityManager em;

    public List<Inscripcion> listarInscripcion(){
        return (List<Inscripcion>) inscripcionDAO.findAll();
    }

    public List<Inscripcion> listarActivos(int activo){
        return inscripcionDAO.findAllByactivo(activo);
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Inscripcion> listarPorIDAlum(int id_alum){
        Query query = em.createQuery("SELECT i FROM Inscripcion i WHERE idalumno = :ida and activo = 1");
        query.setParameter("ida", id_alum);
        return (List<Inscripcion>) query.getResultList();
    }

    public List<Inscripcion> listarPorTerminado(int t){
        return inscripcionDAO.findAllByterminado(t);
    }
    //En desuso. Safe delete mas adelante.
    public Inscripcion guardarInscripcion(Inscripcion i){
        boolean lleno = i.isFull();
        if(lleno){
            return inscripcionDAO.save(i);
        }
        return null;
    }
    @Transactional
    public int guardarIns(Inscripcion i){
        Query query = em.createQuery("INSERT INTO Inscripcion(id_alumno,id_curso,fechainiciocurso,fechafinalcurso,fechainscripcioncurso,terminado,activo, id_frecuencia, id_turno) values(:ida,:idc,:fic,:ffc,:fiic,:t,:a,:idf,:idt)");
        query.setParameter("ida",i.getId_alumno());
        query.setParameter("idc",i.getId_curso());
        query.setParameter("fic",i.getFecha_inicio_curso());
        query.setParameter("ffc", i.getFecha_final_curso());
        query.setParameter("fiic", i.getFecha_inscripcion());
        query.setParameter("t", i.getTerminado());
        query.setParameter("a",i.getActivo());
        query.setParameter("idf", i.getId_frecuencia());
        query.setParameter("idt", i.getId_turno());
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
